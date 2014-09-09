package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Calciform Pools")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class CalciformPools extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public CalciformPools(GameState state)
	{
		super(state, "WU");
	}
}
