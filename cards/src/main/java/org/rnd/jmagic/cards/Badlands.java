package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Badlands")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.SWAMP})
@ColorIdentity({})
public final class Badlands extends Card
{
	public Badlands(GameState state)
	{
		super(state);

	}
}
