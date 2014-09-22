package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Breeding Pool")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.ISLAND})
@ColorIdentity({})
public final class BreedingPool extends Card
{
	public BreedingPool(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
