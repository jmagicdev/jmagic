package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Sunpetal Grove")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SunpetalGrove extends Magic2010DualLand
{
	public SunpetalGrove(GameState state)
	{
		super(state, SubType.FOREST, SubType.PLAINS);
	}
}
