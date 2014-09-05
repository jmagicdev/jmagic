package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dangerous Wager")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class DangerousWager extends Card
{
	public DangerousWager(GameState state)
	{
		super(state);

		// Discard your hand, then draw two cards.
		this.addEffect(discardHand(You.instance(), "Discard your hand,"));
		this.addEffect(drawCards(You.instance(), 2, "then draw two cards."));
	}
}
