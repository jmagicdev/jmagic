package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Geothermal Crevice")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class GeothermalCrevice extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public GeothermalCrevice(GameState state)
	{
		super(state, Color.RED);
	}
}
