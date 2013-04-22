package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Hornet Sting")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class HornetSting extends Card
{
	public HornetSting(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(1, target, "Hornet Sting deals 1 damage to target creature or player."));
	}
}
