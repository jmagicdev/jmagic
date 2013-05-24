package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Omen Machine")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({})
public final class OmenMachine extends Card
{
	public static final class OmenMachineAbility0 extends StaticAbility
	{
		public OmenMachineAbility0(GameState state)
		{
			super(state, "Players can't draw cards.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new SimpleEventPattern(EventType.DRAW_ONE_CARD)));
			this.addEffectPart(part);
		}
	}

	public static final class OmenMachineAbility1 extends EventTriggeredAbility
	{
		public OmenMachineAbility1(GameState state)
		{
			super(state, "At the beginning of each player's draw step, that player exiles the top card of his or her library. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			EventFactory exile = exile(TopCards.instance(1, LibraryOf.instance(thatPlayer)), "That player exiles the top card of his or her library.");
			this.addEffect(exile);

			SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exile));

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "That player puts it onto the battlefield.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, thatPlayer);
			move.parameters.put(EventType.Parameter.OBJECT, thatCard);

			EventFactory cast = new EventFactory(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY, "The player casts it without paying its mana cost if able.");
			cast.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			cast.parameters.put(EventType.Parameter.ALTERNATE_COST, Empty.instance());
			cast.parameters.put(EventType.Parameter.OBJECT, thatCard);

			EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.");
			factory.parameters.put(EventType.Parameter.IF, Intersect.instance(thatCard, HasType.instance(Type.LAND)));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(move));
			factory.parameters.put(EventType.Parameter.ELSE, Identity.instance(cast));
			this.addEffect(factory);
		}
	}

	public OmenMachine(GameState state)
	{
		super(state);

		// Players can't draw cards.
		this.addAbility(new OmenMachineAbility0(state));

		// At the beginning of each player's draw step, that player exiles the
		// top card of his or her library. If it's a land card, the player puts
		// it onto the battlefield. Otherwise, the player casts it without
		// paying its mana cost if able.
		this.addAbility(new OmenMachineAbility1(state));
	}
}
