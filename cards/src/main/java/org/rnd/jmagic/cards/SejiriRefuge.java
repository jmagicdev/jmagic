package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sejiri Refuge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SejiriRefuge extends ZendikarLifeLand
{
	public SejiriRefuge(GameState state)
	{
		super(state, "(WU)");
	}
}
