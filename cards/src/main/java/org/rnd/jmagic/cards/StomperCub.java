package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Stomper Cub")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class StomperCub extends Card
{
	public StomperCub(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
