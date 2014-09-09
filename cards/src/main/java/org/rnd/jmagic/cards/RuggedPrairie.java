package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rugged Prairie")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.RED})
public final class RuggedPrairie extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public RuggedPrairie(GameState state)
	{
		super(state, "R", "W");
	}
}
