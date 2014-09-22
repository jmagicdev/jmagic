package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Savannah")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.FOREST})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class Savannah extends Card
{
	public Savannah(GameState state)
	{
		super(state);

	}
}
