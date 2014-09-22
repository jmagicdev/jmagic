package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Badlands")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.SWAMP})
@ColorIdentity({Color.BLACK, Color.RED})
public final class Badlands extends Card
{
	public Badlands(GameState state)
	{
		super(state);

	}
}
