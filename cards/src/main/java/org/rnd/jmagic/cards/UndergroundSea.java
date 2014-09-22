package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Underground Sea")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.ISLAND})
@ColorIdentity({})
public final class UndergroundSea extends Card
{
	public UndergroundSea(GameState state)
	{
		super(state);

	}
}
