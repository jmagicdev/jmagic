package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Glacial Fortress")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class GlacialFortress extends Magic2010DualLand
{
	public GlacialFortress(GameState state)
	{
		super(state, SubType.PLAINS, SubType.ISLAND);
	}
}
