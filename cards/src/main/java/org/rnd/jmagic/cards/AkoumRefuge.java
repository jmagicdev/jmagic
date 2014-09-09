package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Akoum Refuge")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED})
public final class AkoumRefuge extends ZendikarLifeLand
{
	public AkoumRefuge(GameState state)
	{
		super(state, "(BR)");
	}
}
