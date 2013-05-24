package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dimir Aqueduct")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirAqueduct extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public DimirAqueduct(GameState state)
	{
		super(state, 'U', 'B');
	}
}
