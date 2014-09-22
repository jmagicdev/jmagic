package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hallowed Fountain")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.PLAINS})
@ColorIdentity({})
public final class HallowedFountain extends Card
{
	public HallowedFountain(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
