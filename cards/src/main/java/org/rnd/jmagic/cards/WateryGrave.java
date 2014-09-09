package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Watery Grave")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.ISLAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class WateryGrave extends Card
{
	public WateryGrave(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
