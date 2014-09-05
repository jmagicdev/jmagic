package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Volt Charge")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VoltCharge extends Card
{
	public VoltCharge(GameState state)
	{
		super(state);

		// Volt Charge deals 3 damage to target creature or player. Proliferate.
		// (You choose any number of permanents and/or players with counters on
		// them, then give each another counter of a kind already there.)
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(3, target, "Volt Charge deals 3 damage to target creature or player."));
		this.addEffect(proliferate());
	}
}
