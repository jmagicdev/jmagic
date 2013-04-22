package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx of Uthuun")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("5UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SphinxofUthuun extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SphinxofUthuun", "Put one pile into your hand.", true);

	public static final class SphinxofUthuunAbility1 extends EventTriggeredAbility
	{
		public SphinxofUthuunAbility1(GameState state)
		{
			super(state, "When Sphinx of Uthuun enters the battlefield, reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			EventFactory reveal = reveal(TopCards.instance(5, yourLibrary), "Reveal the top five cards of your library.");
			this.addEffect(reveal);

			EventFactory chooseOpponent = playerChoose(You.instance(), 1, OpponentsOf.instance(You.instance()), PlayerInterface.ChoiceType.PLAYER, PlayerInterface.ChooseReason.AN_OPPONENT, "");
			this.addEffect(chooseOpponent);

			SetGenerator anOpponent = EffectResult.instance(chooseOpponent);
			SetGenerator thoseCards = EffectResult.instance(reveal);
			EventFactory separate = separateIntoPiles(anOpponent, thoseCards, 2, "An opponent separates those cards into two piles.");
			this.addEffect(separate);

			SetGenerator piles = EffectResult.instance(separate);
			EventFactory chooseCards = playerChoose(You.instance(), 1, piles, PlayerInterface.ChoiceType.PILE, REASON, "");
			this.addEffect(chooseCards);

			SetGenerator onePile = EffectResult.instance(chooseCards);
			SetGenerator theOtherPile = RelativeComplement.instance(piles, onePile);
			EventFactory toHand = putIntoHand(ExplodeCollections.instance(onePile), You.instance(), "Put one pile into your hand");
			EventFactory toYard = putIntoGraveyard(ExplodeCollections.instance(theOtherPile), "and the other into your graveyard.");
			this.addEffect(simultaneous(toHand, toYard));
		}
	}

	public SphinxofUthuun(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Sphinx of Uthuun enters the battlefield, reveal the top five
		// cards of your library. An opponent separates those cards into two
		// piles. Put one pile into your hand and the other into your graveyard.
		this.addAbility(new SphinxofUthuunAbility1(state));
	}
}
