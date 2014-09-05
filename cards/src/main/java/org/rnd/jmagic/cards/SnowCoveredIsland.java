package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Snow-Covered Island")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.ISLAND})
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredIsland extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredIsland(GameState state)
	{
		super(state);
	}
}
