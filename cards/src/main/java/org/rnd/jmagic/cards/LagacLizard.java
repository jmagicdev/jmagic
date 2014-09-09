package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lagac Lizard")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class LagacLizard extends Card
{
	public LagacLizard(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
