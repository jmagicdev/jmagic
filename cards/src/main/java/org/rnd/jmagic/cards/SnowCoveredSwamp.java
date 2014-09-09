package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Swamp")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.SWAMP})
@ColorIdentity({})
public final class SnowCoveredSwamp extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredSwamp(GameState state)
	{
		super(state);
	}
}
