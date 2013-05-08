package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Coastal Piracy")
@Types({Type.ENCHANTMENT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CoastalPiracy extends Card
{
	public static final class ApparentlyDrawingCardsIsStealingThings extends EventTriggeredAbility
	{
		public ApparentlyDrawingCardsIsStealingThings(GameState state)
		{
			super(state, "Whenever a creature you control deals combat damage to an opponent, you may draw a card.");

			this.addPattern(whenDealsCombatDamageToAnOpponent(CREATURES_YOU_CONTROL));

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public CoastalPiracy(GameState state)
	{
		super(state);

		// Whenever a creature you control deals combat damage to an opponent,
		// you may draw a card.
		this.addAbility(new ApparentlyDrawingCardsIsStealingThings(state));
	}
}
