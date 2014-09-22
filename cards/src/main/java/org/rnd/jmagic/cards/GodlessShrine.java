package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Godless Shrine")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.PLAINS})
@ColorIdentity({})
public final class GodlessShrine extends Card
{
	public GodlessShrine(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
