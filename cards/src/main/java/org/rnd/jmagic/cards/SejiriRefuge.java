package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Sejiri Refuge")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SejiriRefuge extends ZendikarLifeLand
{
	public SejiriRefuge(GameState state)
	{
		super(state, "(WU)");
	}
}
