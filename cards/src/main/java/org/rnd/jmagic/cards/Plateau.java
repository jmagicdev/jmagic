package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plateau")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.PLAINS})
@ColorIdentity({})
public final class Plateau extends Card
{
	public Plateau(GameState state)
	{
		super(state);

	}
}
