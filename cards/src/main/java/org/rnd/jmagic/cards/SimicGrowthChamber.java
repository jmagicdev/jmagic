package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Simic Growth Chamber")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicGrowthChamber extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public SimicGrowthChamber(GameState state)
	{
		super(state, 'G', 'U');
	}
}
