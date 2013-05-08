package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Graypelt Refuge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GraypeltRefuge extends ZendikarLifeLand
{
	public GraypeltRefuge(GameState state)
	{
		super(state, "(GW)");
	}
}
