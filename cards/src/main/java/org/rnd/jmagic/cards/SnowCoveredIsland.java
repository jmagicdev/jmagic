package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Island")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.ISLAND})
@ColorIdentity({})
public final class SnowCoveredIsland extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredIsland(GameState state)
	{
		super(state);
	}
}
