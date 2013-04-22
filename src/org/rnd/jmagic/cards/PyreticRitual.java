package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Pyretic Ritual")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class PyreticRitual extends Card
{
	public PyreticRitual(GameState state)
	{
		super(state);

		// Add (R)(R)(R) to your mana pool.
		this.addEffect(addManaToYourManaPoolFromSpell("(R)(R)(R)"));
	}
}
