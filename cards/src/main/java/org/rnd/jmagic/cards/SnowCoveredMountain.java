package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Mountain")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN})
@ColorIdentity({})
public final class SnowCoveredMountain extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredMountain(GameState state)
	{
		super(state);
	}
}
