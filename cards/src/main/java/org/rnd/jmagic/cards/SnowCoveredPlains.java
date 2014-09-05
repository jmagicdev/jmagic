package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Snow-Covered Plains")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.PLAINS})
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredPlains extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredPlains(GameState state)
	{
		super(state);
	}
}
