package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Power Sink")
@Types({Type.INSTANT})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PowerSink extends Card
{
	/**
	 * Counter target spell, tap all of [PLAYER]'s lands with mana abilities and
	 * empty his or her mana pool
	 * 
	 * @eparam CAUSE: Power Sink
	 * @eparam TARGET: target of CAUSE
	 * @eparam PLAYER: controller of TARGET
	 * @eparam OBJECT: all of PLAYER's lands; this event type takes care of
	 * determining which lands have mana abilities
	 * @eparam RESULT: empty
	 */
	public static final EventType SINK = new EventType("SINK")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set target = parameters.get(Parameter.TARGET);

			java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
			counterParameters.put(Parameter.CAUSE, cause);
			counterParameters.put(Parameter.OBJECT, target);
			Event counter = createEvent(game, "Counter " + target, COUNTER, counterParameters);
			counter.perform(event, true);

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set toTap = new Set();
			objects: for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				for(NonStaticAbility ability: object.getActual().getNonStaticAbilities())
					if(ability.isManaAbility())
					{
						toTap.add(object);
						continue objects;
					}

			java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
			tapParameters.put(Parameter.CAUSE, cause);
			tapParameters.put(Parameter.OBJECT, toTap);
			Event tap = createEvent(game, "Tap all lands with mana abilities " + player + " controls", TAP_PERMANENTS, tapParameters);
			tap.perform(event, true);

			player = player.getActual();
			java.util.Map<Parameter, Set> emptyParameters = new java.util.HashMap<Parameter, Set>();
			emptyParameters.put(Parameter.CAUSE, cause);
			emptyParameters.put(Parameter.PLAYER, new Set(player));
			Event empty = createEvent(game, "Empty " + player + "'s mana pool", EMPTY_MANA_POOL, emptyParameters);
			empty.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public PowerSink(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (X). If he or she
		// doesn't, that player taps all lands with mana abilities he or she
		// controls and empties his or her mana pool.

		Target target = this.addTarget(Spells.instance(), "target spell");

		SetGenerator controller = ControllerOf.instance(targetedBy(target));

		EventFactory controllerPays = new EventFactory(EventType.PAY_MANA, "Pay (X)");
		controllerPays.parameters.put(EventType.Parameter.CAUSE, This.instance());
		controllerPays.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1")));
		controllerPays.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		controllerPays.parameters.put(EventType.Parameter.PLAYER, controller);

		EventFactory sink = new EventFactory(SINK, "Counter that spell, and that player taps all lands with mana abilities he or she controls and empties his or her mana pool.");
		sink.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sink.parameters.put(EventType.Parameter.PLAYER, controller);
		sink.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		sink.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(LandPermanents.instance(), ControlledBy.instance(controller)));

		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Counter target spell unless its controller pays (X). If he or she doesn't, that player taps all lands with mana abilities he or she controls and empties his or her mana pool.");
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(playerMay(controller, controllerPays, "Target spell's controller may pay (X)")));
		effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(sink));
		this.addEffect(effect);
	}
}
