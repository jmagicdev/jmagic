package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Temple Garden")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.PLAINS})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class TempleGarden extends Card
{
	public TempleGarden(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RavnicaShockLandAbility(state, this.getName()));
	}
}
