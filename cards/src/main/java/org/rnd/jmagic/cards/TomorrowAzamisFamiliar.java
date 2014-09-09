package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tomorrow, Azami's Familiar")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class TomorrowAzamisFamiliar extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Tomorrow, Azami's Familiar", "Choose a card to put into your hand", false);

	public static final class TomorrowAzamisFamiliarAbility0 extends StaticAbility
	{
		public TomorrowAzamisFamiliarAbility0(GameState state)
		{
			super(state, "If you would draw a card, look at the top three cards of your library instead. Put one of those cards into your hand and the rest on the bottom of your library in any order.");

			org.rnd.jmagic.engine.patterns.SimpleEventPattern youDrawACard = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(EventType.DRAW_ONE_CARD);
			youDrawACard.put(EventType.Parameter.PLAYER, You.instance());

			EventReplacementEffect replacement = new EventReplacementEffect(this.game, "If you would draw a card, look at the top three cards of your library instead. Put one of those cards into your hand and the rest on the bottom of your library in any order.", youDrawACard);

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator topThreeCardsOfYourLibrary = TopCards.instance(3, yourLibrary);

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top three cards of your library instead.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topThreeCardsOfYourLibrary);
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			replacement.addEffect(look);

			EventFactory moveIntoHand = new EventFactory(EventType.MOVE_CHOICE, "Put one of those cards into your hand");
			moveIntoHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveIntoHand.parameters.put(EventType.Parameter.OBJECT, topThreeCardsOfYourLibrary);
			moveIntoHand.parameters.put(EventType.Parameter.CHOICE, Identity.instance(REASON));
			moveIntoHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			moveIntoHand.parameters.put(EventType.Parameter.PLAYER, You.instance());
			replacement.addEffect(moveIntoHand);

			EventFactory moveOntoBottom = new EventFactory(EventType.MOVE_OBJECTS, "and the rest on the bottom of your library in any order.");
			moveOntoBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveOntoBottom.parameters.put(EventType.Parameter.TO, yourLibrary);
			moveOntoBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			// This is kind of hacky, but after you put one of the cards into
			// your hand, "the rest" are the top two cards of your library
			moveOntoBottom.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(2, yourLibrary));
			replacement.addEffect(moveOntoBottom);

			this.addEffectPart(replacementEffectPart(replacement));

		}
	}

	public TomorrowAzamisFamiliar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		// If you would draw a card, look at the top three cards of your library
		// instead. Put one of those cards into your hand and the rest on the
		// bottom of your library in any order.
		this.addAbility(new TomorrowAzamisFamiliarAbility0(state));
	}
}
