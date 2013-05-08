package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fact or Fiction")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class FactorFiction extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("FactOrFiction", "Put a pile into your hand.", true);

	public FactorFiction(GameState state)
	{
		super(state);

		// Reveal the top five cards of your library.
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		SetGenerator topFive = TopCards.instance(5, yourLibrary);
		this.addEffect(reveal(topFive, "Reveal the top five cards of your library."));

		// An opponent separates those cards into two piles.
		EventFactory chooseOpponent = playerChoose(You.instance(), 1, OpponentsOf.instance(You.instance()), PlayerInterface.ChoiceType.PLAYER, PlayerInterface.ChooseReason.AN_OPPONENT, "");
		this.addEffect(chooseOpponent);

		SetGenerator anOpponent = EffectResult.instance(chooseOpponent);
		EventFactory separate = separateIntoPiles(anOpponent, topFive, 2, "An opponent separates those cards into two piles.");
		this.addEffect(separate);

		// Put one pile into your hand and the other into your graveyard.
		SetGenerator piles = EffectResult.instance(separate);
		EventFactory choosePile = playerChoose(You.instance(), 1, piles, PlayerInterface.ChoiceType.PILE, REASON, "");
		this.addEffect(choosePile);

		SetGenerator onePile = EffectResult.instance(choosePile);
		SetGenerator theOtherPile = RelativeComplement.instance(piles, onePile);
		EventFactory toHand = putIntoHand(ExplodeCollections.instance(onePile), You.instance(), "Put one pile into your hand");
		EventFactory toYard = putIntoGraveyard(ExplodeCollections.instance(theOtherPile), "and the other into your graveyard.");
		this.addEffect(simultaneous(toHand, toYard));
	}
}
