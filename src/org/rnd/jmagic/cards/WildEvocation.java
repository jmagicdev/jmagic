package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wild Evocation")
@Types({Type.ENCHANTMENT})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WildEvocation extends Card
{
	public static final class WildEvocationAbility0 extends EventTriggeredAbility
	{
		/**
		 * @eparam CAUSE: the cause of the cast
		 * @eparam PLAYER: the player casting the spell
		 * @eparam OBJECT: the spell to cast
		 * @eparam RESULT: if the spell can be cast, the result of the cast.
		 * empty otherwise.
		 */
		public static final EventType CAST_WITHOUT_PAYING_MANA_COST_IF_ABLE = new EventType("CAST_WITHOUT_PAYING_MANA_COST_IF_ABLE")
		{
			@Override
			public Parameter affects()
			{
				return Parameter.PLAYER;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
				GameObject card = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

				java.util.Map<Parameter, Set> castParameters = new java.util.HashMap<Parameter, Set>();
				castParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				castParameters.put(Parameter.PLAYER, new Set(player));
				castParameters.put(Parameter.ALTERNATE_COST, Empty.set);
				castParameters.put(Parameter.OBJECT, new Set(card));
				Event cast = createEvent(game, player + " casts " + card, PLAY_CARD, castParameters);
				if(!cast.attempt(event))
				{
					event.setResult(Empty.set);
					return false;
				}

				while(true)
				{
					cast = createEvent(game, player + " casts " + card, PLAY_CARD, castParameters);
					if(cast.perform(event, false))
						break;
				}

				event.setResult(cast.getResultGenerator());

				return true;
			}
		};

		public WildEvocationAbility0(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player reveals a card at random from his or her hand. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.");

			this.addPattern(atTheBeginningOfEachPlayersUpkeep());

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			EventFactory reveal = new EventFactory(EventType.REVEAL_RANDOM_FROM_HAND, "That player reveals a card at random from his or her hand.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			reveal.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			this.addEffect(reveal);

			SetGenerator thatCard = EffectResult.instance(reveal);

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "The player puts it onto the battlefield.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, thatPlayer);
			move.parameters.put(EventType.Parameter.OBJECT, thatCard);

			EventFactory cast = new EventFactory(CAST_WITHOUT_PAYING_MANA_COST_IF_ABLE, "The player casts it without paying its mana cost if able.");
			cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cast.parameters.put(EventType.Parameter.OBJECT, thatCard);
			cast.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

			EventFactory ifLandFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.");
			ifLandFactory.parameters.put(EventType.Parameter.IF, Intersect.instance(thatCard, HasType.instance(Type.LAND)));
			ifLandFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(move));
			ifLandFactory.parameters.put(EventType.Parameter.ELSE, Identity.instance(cast));

			// This last IF is to handle the case where a card wasn't revealed
			// (for instance, the hand was empty)
			EventFactory ifRevealFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.");
			ifRevealFactory.parameters.put(EventType.Parameter.IF, thatCard);
			ifRevealFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(ifLandFactory));
			this.addEffect(ifRevealFactory);
		}
	}

	public WildEvocation(GameState state)
	{
		super(state);

		// At the beginning of each player's upkeep, that player reveals a card
		// at random from his or her hand. If it's a land card, the player puts
		// it onto the battlefield. Otherwise, the player casts it without
		// paying its mana cost if able.
		this.addAbility(new WildEvocationAbility0(state));
	}
}
