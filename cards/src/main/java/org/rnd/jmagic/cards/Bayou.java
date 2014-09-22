package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bayou")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.SWAMP})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class Bayou extends Card
{
	public Bayou(GameState state)
	{
		super(state);

	}
}
