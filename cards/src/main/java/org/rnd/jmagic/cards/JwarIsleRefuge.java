package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Jwar Isle Refuge")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class JwarIsleRefuge extends ZendikarLifeLand
{
	public JwarIsleRefuge(GameState state)
	{
		super(state, "(UB)");
	}
}
