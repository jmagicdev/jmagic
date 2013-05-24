package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shattered Perception")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ShatteredPerception extends Card
{
	public ShatteredPerception(GameState state)
	{
		super(state);

		// Discard all the cards in your hand, then draw that many cards.
		EventFactory discard = discardHand(You.instance(), "Discard all the cards in your hand,");
		this.addEffect(discard);
		this.addEffect(drawCards(You.instance(), Count.instance(EffectResult.instance(discard)), "then draw that many cards."));

		// Flashback (5)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(R)"));
	}
}
