package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mystic Gate")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class MysticGate extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public MysticGate(GameState state)
	{
		super(state, "W", "U");
	}
}
