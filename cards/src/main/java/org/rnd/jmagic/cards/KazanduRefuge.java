package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Kazandu Refuge")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class KazanduRefuge extends ZendikarLifeLand
{
	public KazanduRefuge(GameState state)
	{
		super(state, "(RG)");
	}
}
