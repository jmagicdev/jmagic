package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Irrigation Ditch")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class IrrigationDitch extends org.rnd.jmagic.cardTemplates.InvasionLand
{
	public IrrigationDitch(GameState state)
	{
		super(state, Color.WHITE);
	}
}
