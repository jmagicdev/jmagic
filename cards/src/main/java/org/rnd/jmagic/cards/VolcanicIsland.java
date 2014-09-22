package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Volcanic Island")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.ISLAND})
@ColorIdentity({Color.BLUE, Color.RED})
public final class VolcanicIsland extends Card
{
	public VolcanicIsland(GameState state)
	{
		super(state);

	}
}
