package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sulfur Vent")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class SulfurVent extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public SulfurVent(GameState state)
	{
		super(state, Color.BLACK);
	}
}
