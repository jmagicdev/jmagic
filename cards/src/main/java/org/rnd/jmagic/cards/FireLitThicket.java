package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fire-Lit Thicket")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FireLitThicket extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public FireLitThicket(GameState state)
	{
		super(state, "R", "G");
	}
}
