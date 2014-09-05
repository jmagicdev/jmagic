package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sulfur Vent")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class SulfurVent extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public SulfurVent(GameState state)
	{
		super(state, Color.BLACK);
	}
}
