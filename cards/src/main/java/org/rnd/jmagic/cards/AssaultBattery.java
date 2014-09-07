package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Assault // Battery")
@Types({Type.SORCERY})
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.SPECIAL), @Printings.Printed(ex = Planechase.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class AssaultBattery extends SplitCard
{
	public AssaultBattery(GameState state)
	{
		super(state, Assault.class, Battery.class);
	}
}
