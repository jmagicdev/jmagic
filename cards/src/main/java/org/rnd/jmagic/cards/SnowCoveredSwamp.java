package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Snow-Covered Swamp")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.SWAMP})
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredSwamp extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredSwamp(GameState state)
	{
		super(state);
	}
}
