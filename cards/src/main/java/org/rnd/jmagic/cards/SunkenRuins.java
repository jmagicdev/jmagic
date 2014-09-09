package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sunken Ruins")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SunkenRuins extends org.rnd.jmagic.cardTemplates.ShadowmoorDualLand
{
	public SunkenRuins(GameState state)
	{
		super(state, "U", "B");
	}
}
