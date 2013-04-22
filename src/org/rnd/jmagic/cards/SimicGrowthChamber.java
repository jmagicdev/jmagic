package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Simic Growth Chamber")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicGrowthChamber extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public SimicGrowthChamber(GameState state)
	{
		super(state, 'G', 'U');
	}
}
