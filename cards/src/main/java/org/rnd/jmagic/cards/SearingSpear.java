package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Searing Spear")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SearingSpear extends Card
{
	public SearingSpear(GameState state)
	{
		super(state);

		// Searing Spear deals 3 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Searing Spear deals 3 damage to target creature or player."));
	}
}
