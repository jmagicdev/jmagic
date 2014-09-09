package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Twilight Mire")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class TwilightMire extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public TwilightMire(GameState state)
	{
		super(state, "B", "G");
	}
}
