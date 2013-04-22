package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Island")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.ISLAND})
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredIsland extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredIsland(GameState state)
	{
		super(state);
	}
}
