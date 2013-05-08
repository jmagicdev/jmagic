package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Swamp")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.SWAMP})
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredSwamp extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredSwamp(GameState state)
	{
		super(state);
	}
}
