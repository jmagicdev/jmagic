package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snow-Covered Forest")
@SuperTypes({SuperType.BASIC, SuperType.SNOW})
@Types({Type.LAND})
@SubTypes({SubType.FOREST})
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.LAND)})
@ColorIdentity({})
public final class SnowCoveredForest extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public SnowCoveredForest(GameState state)
	{
		super(state);
	}
}
