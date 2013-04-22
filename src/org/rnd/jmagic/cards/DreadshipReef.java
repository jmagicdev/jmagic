package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dreadship Reef")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DreadshipReef extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public DreadshipReef(GameState state)
	{
		super(state, "UB");
	}
}
