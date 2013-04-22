package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Flame Javelin")
@Types({Type.INSTANT})
@ManaCost("(2/R)(2/R)(2/R)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FlameJavelin extends Card
{
	public FlameJavelin(GameState state)
	{
		super(state);

		// Flame Javelin deals 4 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(4, target, "Flame Javelin deals 4 damage to target creature or player."));
	}
}
