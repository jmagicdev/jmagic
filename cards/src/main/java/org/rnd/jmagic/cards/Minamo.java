package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Minamo")
@Types({Type.PLANE})
@SubTypes({SubType.KAMIGAWA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Minamo extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Minamo", "Return a blue card from your graveyard to your hand?", true);

	public static final class Cantrips extends EventTriggeredAbility
	{
		public Cantrips(GameState state)
		{
			super(state, "Whenever a player casts a spell, that player may draw a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);

			this.addEffect(playerMay(thatPlayer, drawCards(thatPlayer, 1, "That player draws a card."), "That player may draw a card."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class BlueChaos extends EventTriggeredAbility
	{
		/**
		 * @eparam CAUSE: Minamo's ability
		 * @eparam PLAYER: The players who will be given the choice
		 * @eparam CHOICE: The filter to run against the players graveyard
		 * @eparam RESULT: empty
		 */
		public static final EventType BLUE_CHAOS_EFFECT = new EventType("BLUE_CHAOS_EFFECT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Set filter = parameters.get(EventType.Parameter.CHOICE);

				for(Player player: parameters.get(EventType.Parameter.PLAYER).getAll(Player.class))
				{
					Set allowedCards = Intersect.get(filter, Set.fromCollection(player.getGraveyard(game.actualState).objects));
					if(allowedCards.isEmpty())
					{
						// They can't put a card onto the battlefield if they
						// don't have one
						event.putChoices(player, java.util.Collections.emptySet());
						continue;
					}

					java.util.List<Answer> yesNo = player.sanitizeAndChoose(game.actualState, 1, Answer.mayChoices(), PlayerInterface.ChoiceType.ENUM, REASON);
					if(yesNo.iterator().next() == Answer.NO)
						// They won't put a card onto the battlefield if they
						// choose not to
						continue;

					// Choose a card to put onto the battlefield
					java.util.List<GameObject> choice = player.sanitizeAndChoose(game.actualState, 1, allowedCards.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.BOUNCE);
					event.putChoices(player, choice);
				}
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
				{
					player = player.getActual();
					java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
					moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					moveParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
					moveParameters.put(Parameter.OBJECT, Set.fromCollection(event.getChoices(player)));
					Event move = createEvent(game, "Put the chosen card into its owners' hand.", EventType.MOVE_OBJECTS, moveParameters);
					move.perform(event, true);
				}

				event.setResult(Empty.set);
				return true;
			}
		};

		public BlueChaos(GameState state)
		{
			super(state, "Whenever you roll (C), each player may return a blue card from his or her graveyard to his or her hand.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			EventFactory factory = new EventFactory(BLUE_CHAOS_EFFECT, "Each player may return a blue card from his or her graveyard to his or hand.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, Players.instance());
			factory.parameters.put(EventType.Parameter.CHOICE, HasColor.instance(Color.BLUE));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public Minamo(GameState state)
	{
		super(state);

		this.addAbility(new Cantrips(state));

		this.addAbility(new BlueChaos(state));
	}
}
