package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tropical Island")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.FOREST})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class TropicalIsland extends Card
{
	public TropicalIsland(GameState state)
	{
		super(state);

	}
}
