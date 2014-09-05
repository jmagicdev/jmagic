package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Snow-Covered Mountain")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN})
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredMountain extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredMountain(GameState state)
	{
		super(state);
	}
}
