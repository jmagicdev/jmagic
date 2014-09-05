package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fungal Reaches")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FungalReaches extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public FungalReaches(GameState state)
	{
		super(state, "RG");
	}
}
