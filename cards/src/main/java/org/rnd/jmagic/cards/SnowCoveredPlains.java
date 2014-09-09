package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Plains")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.PLAINS})
@ColorIdentity({})
public final class SnowCoveredPlains extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredPlains(GameState state)
	{
		super(state);
	}
}
