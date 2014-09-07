package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Assault")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.SPECIAL), @Printings.Printed(ex = Planechase.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Assault extends Card
{
	public Assault(GameState state)
	{
		super(state);

		// Assault deals 2 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Assault deals 2 damage to target creature or player."));
	}
}
