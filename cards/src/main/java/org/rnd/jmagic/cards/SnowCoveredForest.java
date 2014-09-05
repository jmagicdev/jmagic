package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Snow-Covered Forest")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.FOREST})
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredForest extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredForest(GameState state)
	{
		super(state);
	}
}
