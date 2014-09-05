package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Geothermal Crevice")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class GeothermalCrevice extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public GeothermalCrevice(GameState state)
	{
		super(state, Color.RED);
	}
}
