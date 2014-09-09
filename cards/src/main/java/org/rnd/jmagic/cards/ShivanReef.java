package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shivan Reef")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.RED})
public final class ShivanReef extends org.rnd.jmagic.cardTemplates.PainLand
{
	public ShivanReef(GameState state)
	{
		super(state, "UR");
	}
}
