package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mundungu")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1UB")
@Printings({@Printings.Printed(ex = Expansion.VISIONS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class Mundungu extends Card
{
	/**
	 * @eparam CAUSE: Mundungu's ability
	 * @eparam PLAYER: who is paying
	 * @eparam RESULT: empty
	 */
	public static final EventType PLAYER_MAY_PAY_1_MANA_AND_1_LIFE = new EventType("PLAYER_MAY_PAY_1_MANA_AND_1_LIFE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			Set playerParameter = parameters.get(Parameter.PLAYER);
			Player player = playerParameter.getOne(Player.class);
			player.mayActivateManaAbilities();

			if(player.pool.isEmpty())
				return false;

			java.util.Map<Parameter, Set> lifeParameters = new java.util.HashMap<Parameter, Set>();
			lifeParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			lifeParameters.put(Parameter.PLAYER, new Set(player));
			lifeParameters.put(Parameter.NUMBER, ONE);
			Event payLife = createEvent(game, player + " pays 1 life", EventType.PAY_LIFE, lifeParameters);
			if(!payLife.attempt(event))
				return false;

			ManaPool cost = new ManaPool("1");
			EventFactory factory = new EventFactory(PAY_MANA, "Pay (1)");
			factory.parameters.put(Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
			factory.parameters.put(Parameter.COST, Identity.instance(cost));
			factory.parameters.put(Parameter.PLAYER, Identity.instance(playerParameter));

			java.util.Map<Parameter, Set> mayPayParameters = new java.util.HashMap<Parameter, Set>();
			mayPayParameters.put(Parameter.PLAYER, playerParameter);
			mayPayParameters.put(Parameter.EVENT, new Set(factory));
			Event mayPay = createEvent(game, player + " may pay (1)", PLAYER_MAY, mayPayParameters);
			boolean ret = mayPay.perform(event, false);
			if(!ret)
				return false;

			payLife.perform(event, false);
			return true;
		}
	};

	public static final class CounterStuff extends ActivatedAbility
	{
		public CounterStuff(GameState state)
		{
			super(state, "(T): Counter target spell unless its controller pays (1) and 1 life.");
			this.costsTap = true;

			Target target = this.addTarget(Spells.instance(), "target spell");
			EventFactory counter = counter(targetedBy(target), "Counter target spell");

			SetGenerator controller = ControllerOf.instance(targetedBy(target));
			EventFactory pay = new EventFactory(PLAYER_MAY_PAY_1_MANA_AND_1_LIFE, "Pay (1) and 1 life");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.PLAYER, controller);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Counter target spell unless its controller pays (1) and 1 life.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(pay));
			effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(counter));
			this.addEffect(effect);
		}
	}

	public Mundungu(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Counter target spell unless its controller pays (1) and 1 life.
		this.addAbility(new CounterStuff(state));
	}
}
