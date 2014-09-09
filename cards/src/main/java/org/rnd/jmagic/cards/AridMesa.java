package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Arid Mesa")
@Types({Type.LAND})
@ColorIdentity({})
public final class AridMesa extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public AridMesa(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.PLAINS);
	}
}
