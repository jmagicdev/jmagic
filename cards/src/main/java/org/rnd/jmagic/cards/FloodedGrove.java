package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Flooded Grove")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class FloodedGrove extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public FloodedGrove(GameState state)
	{
		super(state, "G", "U");
	}
}
