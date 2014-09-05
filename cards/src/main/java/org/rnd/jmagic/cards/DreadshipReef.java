package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dreadship Reef")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DreadshipReef extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public DreadshipReef(GameState state)
	{
		super(state, "UB");
	}
}
