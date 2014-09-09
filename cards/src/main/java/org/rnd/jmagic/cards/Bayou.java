package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bayou")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.SWAMP})
@ColorIdentity({})
public final class Bayou extends Card
{
	public Bayou(GameState state)
	{
		super(state);

	}
}
