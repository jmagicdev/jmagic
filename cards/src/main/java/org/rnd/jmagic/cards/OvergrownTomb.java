package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Overgrown Tomb")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.FOREST})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class OvergrownTomb extends Card
{
	public OvergrownTomb(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
