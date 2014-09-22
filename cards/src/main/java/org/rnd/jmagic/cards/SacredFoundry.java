package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sacred Foundry")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.MOUNTAIN})
@ColorIdentity({})
public final class SacredFoundry extends Card
{
	public SacredFoundry(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
