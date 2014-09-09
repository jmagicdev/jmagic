package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Misty Rainforest")
@Types({Type.LAND})
@ColorIdentity({})
public final class MistyRainforest extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public MistyRainforest(GameState state)
	{
		super(state, SubType.FOREST, SubType.ISLAND);
	}
}
