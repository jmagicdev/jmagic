package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Faithless Looting")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FaithlessLooting extends Card
{
	public FaithlessLooting(GameState state)
	{
		super(state);

		// Draw two cards, then discard two cards.
		this.addEffect(drawCards(You.instance(), 2, "Draw two cards,"));
		this.addEffect(discardCards(You.instance(), 2, "then discard two cards."));

		// Flashback (2)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(R)"));
	}
}
