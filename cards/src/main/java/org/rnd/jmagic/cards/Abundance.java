package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Abundance")
@Types({Type.ENCHANTMENT})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Abundance extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Abundance", "Choose land or nonland.", true);

	public static final class CakeRoll extends StaticAbility
	{
		/**
		 * @eparam CAUSE: the cause of this nonsense
		 * @eparam PLAYER: the player making the choice and drawing
		 * @eparam RESULT: empty
		 */
		public static final EventType ABUNDANCE_EVENT = new EventType("ABUNDANCE_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				boolean ret;

				Player player = parameters.get(EventType.Parameter.PLAYER).getOne(Player.class);
				Answer choice = player.choose(1, java.util.EnumSet.of(Answer.LAND, Answer.NONLAND), PlayerInterface.ChoiceType.ENUM, REASON).iterator().next();

				Set toReveal = new Set();
				Set toDraw = new Set();

				boolean answerIsNonLand = (choice == Answer.NONLAND);
				Zone library = player.getLibrary(game.actualState);
				for(GameObject card: library.objects)
				{
					toReveal.add(card);
					if(answerIsNonLand != card.getTypes().contains(Type.LAND))
					{
						toDraw.add(card);
						break;
					}
				}

				Set cause = parameters.get(EventType.Parameter.CAUSE);
				java.util.Map<EventType.Parameter, Set> revealParameters = new java.util.HashMap<EventType.Parameter, Set>();
				revealParameters.put(EventType.Parameter.CAUSE, cause);
				revealParameters.put(EventType.Parameter.OBJECT, toReveal);
				Event revealEvent = createEvent(game, "Reveal cards form the top of your library until you reveal a card of the chosen kind", EventType.REVEAL, revealParameters);
				ret = revealEvent.perform(event, true);

				java.util.Map<EventType.Parameter, Set> moveToHandParameters = new java.util.HashMap<EventType.Parameter, Set>();
				moveToHandParameters.put(EventType.Parameter.CAUSE, cause);
				moveToHandParameters.put(EventType.Parameter.TO, new Set(player.getHand(game.actualState)));
				moveToHandParameters.put(EventType.Parameter.OBJECT, toDraw);
				Event moveToHand = createEvent(game, "Put that card into your hand", EventType.MOVE_OBJECTS, moveToHandParameters);
				ret = moveToHand.perform(event, true) && ret;

				java.util.Map<EventType.Parameter, Set> moveToLibraryParameters = new java.util.HashMap<EventType.Parameter, Set>();
				moveToLibraryParameters.put(EventType.Parameter.CAUSE, cause);
				moveToLibraryParameters.put(EventType.Parameter.TO, new Set(library));
				moveToLibraryParameters.put(EventType.Parameter.INDEX, NEGATIVE_ONE);
				moveToLibraryParameters.put(EventType.Parameter.OBJECT, toReveal);
				Event moveToLibrary = createEvent(game, "Put all other cards revealed this way on the bottom of your library in any order", EventType.MOVE_OBJECTS, moveToLibraryParameters);
				ret = moveToLibrary.perform(event, true) && ret;

				event.setResult(Empty.set);
				return ret;
			}
		};

		public CakeRoll(GameState state)
		{
			super(state, "If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order.");

			SimpleEventPattern youDrawACard = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			youDrawACard.put(EventType.Parameter.PLAYER, You.instance());

			EventReplacementEffect replacement = new EventReplacementEffect(this.game, "You may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order.", youDrawACard);
			replacement.makeOptional(You.instance());

			replacement.addEffect(new EventFactory(ABUNDANCE_EVENT, new EventType.ParameterMap(), "Choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order."));
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public Abundance(GameState state)
	{
		super(state);

		this.addAbility(new CakeRoll(state));
	}
}
