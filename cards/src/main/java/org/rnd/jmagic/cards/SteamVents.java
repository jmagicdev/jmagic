package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Steam Vents")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.MOUNTAIN})
@ColorIdentity({Color.BLUE, Color.RED})
public final class SteamVents extends Card
{
	public SteamVents(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
