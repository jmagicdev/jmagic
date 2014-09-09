package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Forest")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.FOREST})
@ColorIdentity({})
public final class SnowCoveredForest extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredForest(GameState state)
	{
		super(state);
	}
}
