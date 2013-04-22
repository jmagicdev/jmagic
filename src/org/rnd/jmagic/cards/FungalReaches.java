package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fungal Reaches")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FungalReaches extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public FungalReaches(GameState state)
	{
		super(state, "RG");
	}
}
