package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blood Crypt")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.MOUNTAIN})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BloodCrypt extends Card
{
	public BloodCrypt(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
