package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Massive Raid")
@Types({Type.INSTANT})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MassiveRaid extends Card
{
	public MassiveRaid(GameState state)
	{
		super(state);

		// Massive Raid deals damage to target creature or player equal to the
		// number of creatures you control.
		SetGenerator num = Count.instance(CREATURES_YOU_CONTROL);
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(num, target, "Massive Raid deals damage to target creature or player equal to the number of creatures you control."));
	}
}
