package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Taiga")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.FOREST})
@ColorIdentity({Color.RED, Color.GREEN})
public final class Taiga extends Card
{
	public Taiga(GameState state)
	{
		super(state);

	}
}
