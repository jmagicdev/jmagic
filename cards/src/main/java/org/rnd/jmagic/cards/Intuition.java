package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Intuition")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Intuition extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Intuition", "Choose a card for Intuition's controller to put into his or her hand.", true);

	public Intuition(GameState state)
	{
		super(state);

		// Search your library for any three cards
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for any three cards");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
		search.parameters.put(EventType.Parameter.CARD, yourLibrary);
		this.addEffect(search);

		// and reveal them.
		SetGenerator foundCards = Intersect.instance(EffectResult.instance(search), Cards.instance());
		EventFactory reveal = reveal(foundCards, "and reveal them.");
		this.addEffect(reveal);

		// Target opponent chooses one.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		EventFactory choose = playerChoose(target, 1, foundCards, PlayerInterface.ChoiceType.OBJECTS, REASON, "Target opponent chooses one.");
		this.addEffect(choose);

		// Put that card into your hand and the rest into your graveyard.
		SetGenerator thatCard = EffectResult.instance(choose);
		EventFactory toHand = putIntoHand(thatCard, You.instance(), "Put that card into your hand");
		EventFactory toYard = putIntoGraveyard(RelativeComplement.instance(foundCards, thatCard), "and the rest into your graveyard.");
		this.addEffect(simultaneous(toHand, toYard));

		// Then shuffle your library.
		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
