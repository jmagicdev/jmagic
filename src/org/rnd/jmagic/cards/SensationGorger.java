package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sensation Gorger")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class SensationGorger extends Card
{
	public static final class WheelOfKinship extends org.rnd.jmagic.abilityTemplates.Kinship
	{
		/**
		 * We only need this custom event type because it is used in the THEN
		 * parameter of an IF_EVENT_THEN_ELSE event, and needs to perform two
		 * events in order.
		 * 
		 * @eparam CAUSE: the kinship ability
		 * @eparam PLAYER: the players discarding and drawing
		 * @eparam RESULT: empty
		 */
		public static final EventType DISCARD_AND_DRAW = new EventType("DISCARD_AND_DRAW")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Set cards = new Set();

				for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
					cards.addAll(player.getHand(game.actualState).objects);

				java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
				discardParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				discardParameters.put(EventType.Parameter.CARD, cards);
				Event discardEvent = createEvent(game, "Each player discards his or her hand", EventType.DISCARD_CARDS, discardParameters);
				discardEvent.perform(event, true);

				java.util.Map<Parameter, Set> drawParameters = new java.util.HashMap<Parameter, Set>();
				drawParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				drawParameters.put(EventType.Parameter.PLAYER, parameters.get(Parameter.PLAYER));
				drawParameters.put(EventType.Parameter.NUMBER, new Set(4));
				Event drawEvent = createEvent(game, "and draws four cards.", EventType.DRAW_CARDS, drawParameters);
				drawEvent.perform(event, true);

				event.setResult(Empty.set);

				return true;
			}
		};

		public WheelOfKinship(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Sensation Gorger, you may reveal it. If you do, each player discards his or her hand and draws four cards.");
		}

		@Override
		protected EventFactory getKinshipEffect()
		{
			EventFactory factory = new EventFactory(DISCARD_AND_DRAW, "Each player discards his or her hand and draws four cards.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, Players.instance());
			return factory;
		}
	}

	public SensationGorger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new WheelOfKinship(state));
	}
}
