package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tranquil Thicket")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN})
public final class TranquilThicket extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public TranquilThicket(GameState state)
	{
		super(state, "(G)");
	}
}
