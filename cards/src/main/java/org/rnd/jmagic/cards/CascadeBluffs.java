package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cascade Bluffs")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.RED})
public final class CascadeBluffs extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public CascadeBluffs(GameState state)
	{
		super(state, "U", "R");
	}
}
