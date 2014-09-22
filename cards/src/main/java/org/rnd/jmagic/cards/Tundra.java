package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tundra")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.ISLAND})
@ColorIdentity({})
public final class Tundra extends Card
{
	public Tundra(GameState state)
	{
		super(state);

	}
}
