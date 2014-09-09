package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Irrigation Ditch")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class IrrigationDitch extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public IrrigationDitch(GameState state)
	{
		super(state, Color.WHITE);
	}
}
