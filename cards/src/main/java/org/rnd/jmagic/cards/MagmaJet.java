package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Magma Jet")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = FifthDawn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class MagmaJet extends Card
{
	public MagmaJet(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(2, targetedBy(target), "Magma Jet deals 2 damage to target creature or player."));

		this.addEffect(scry(2, "\n\nScry 2."));
	}
}
