package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scrubland")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.PLAINS})
@ColorIdentity({})
public final class Scrubland extends Card
{
	public Scrubland(GameState state)
	{
		super(state);

	}
}
