package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Stomping Ground")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.MOUNTAIN})
@ColorIdentity({Color.RED, Color.GREEN})
public final class StompingGround extends Card
{
	public StompingGround(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
