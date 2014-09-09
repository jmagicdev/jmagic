package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Simic Growth Chamber")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicGrowthChamber extends org.rnd.jmagic.cardTemplates.RavnicaBounceLand
{
	public SimicGrowthChamber(GameState state)
	{
		super(state, 'G', 'U');
	}
}
