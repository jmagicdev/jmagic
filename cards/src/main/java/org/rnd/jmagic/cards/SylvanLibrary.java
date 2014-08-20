package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvan Library")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class SylvanLibrary extends Card
{
	/**
	 * @eparam OBJECT: the cards you chose
	 */
	public static final EventType PAY_OR_TOSS = new EventType("PAY_OR_TOSS")
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

			Set cards = parameters.get(Parameter.OBJECT);
			if(cards.size() == 0)
				return true;

			GameObject cause = event.getSource();
			Player you = cause.getController(game.actualState);

			Set events = new Set();

			if(cards.size() == 2)
			{
				events.add(payLife(You.instance(), 8, "Pay 8 life"));

				java.util.Iterator<GameObject> i = cards.getAll(GameObject.class).iterator();
				GameObject firstCard = i.next();
				EventFactory putFirstBack = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put " + firstCard + " on top of your library");
				putFirstBack.parameters.put(EventType.Parameter.CAUSE, Identity.instance(cause));
				putFirstBack.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
				putFirstBack.parameters.put(EventType.Parameter.OBJECT, Identity.instance(firstCard));

				GameObject secondCard = i.next();
				EventFactory putSecondBack = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put " + secondCard + " on top of your library");
				putSecondBack.parameters.put(EventType.Parameter.CAUSE, Identity.instance(cause));
				putSecondBack.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
				putSecondBack.parameters.put(EventType.Parameter.OBJECT, Identity.instance(secondCard));

				EventFactory pay4Life = payLife(You.instance(), 4, "Pay 4 life,");

				events.add(simultaneous(pay4Life, putFirstBack));
				events.add(simultaneous(pay4Life, putSecondBack));
				events.add(simultaneous("Put both cards on top of your library", putFirstBack, putSecondBack));
			}
			else if(cards.size() == 1)
			{
				events.add(payLife(You.instance(), 4, "Pay 4 life"));

				GameObject card = cards.getOne(GameObject.class);
				EventFactory putCardBack = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put " + card + " on top of your library");
				putCardBack.parameters.put(EventType.Parameter.CAUSE, Identity.instance(cause));
				putCardBack.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
				putCardBack.parameters.put(EventType.Parameter.OBJECT, Identity.instance(card));
				events.add(putCardBack);
			}
			else
			{
				throw new IllegalStateException("Sylvan Library effect with more than two cards?!??!");
			}

			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			newParameters.put(EventType.Parameter.PLAYER, new Set(you));
			newParameters.put(EventType.Parameter.EVENT, Set.fromCollection(events));
			Event doIt = createEvent(game, "For each of those cards, pay 4 life or put the card on top of your library", EventType.CHOOSE_AND_PERFORM, newParameters);
			doIt.perform(event, true);

			return true;
		}

	};

	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SylvanLibrary", "Choose two cards in your hand drawn this turn.", false);

	public static final class SylvanLibraryAbility0 extends EventTriggeredAbility
	{
		public SylvanLibraryAbility0(GameState state)
		{
			super(state, "At the beginning of your draw step, you may draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.");

			this.addPattern(atTheBeginningOfYourDrawStep());

			EventFactory youMayDraw = youMay(drawCards(You.instance(), 2, "Draw two additional cards"), "You may draw two additional cards");
			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
			state.ensureTracker(new DrawnThisTurn.DrawTracker());

			EventFactory chooseTwoCards = new EventFactory(EventType.PLAYER_CHOOSE, "Choose two cards in your hand drawn this turn.");
			chooseTwoCards.parameters.put(EventType.Parameter.PLAYER, You.instance());
			chooseTwoCards.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			chooseTwoCards.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(inYourHand, DrawnThisTurn.instance()));
			chooseTwoCards.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));

			EventFactory payOrToss = new EventFactory(PAY_OR_TOSS, "For each of those cards, pay 4 life or put the card on top of your library.");
			payOrToss.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(chooseTwoCards));

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may draw two additional cards. If you do, choose two cards in your hand drawn this turn. For each of those cards, pay 4 life or put the card on top of your library.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMayDraw));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(chooseTwoCards, payOrToss)));
			this.addEffect(effect);

		}
	}

	public SylvanLibrary(GameState state)
	{
		super(state);

		// At the beginning of your draw step, you may draw two additional
		// cards. If you do, choose two cards in your hand drawn this turn. For
		// each of those cards, pay 4 life or put the card on top of your
		// library.
		this.addAbility(new SylvanLibraryAbility0(state));
	}
}
