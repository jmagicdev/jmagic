package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hill Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class HillGiant extends Card
{
	public HillGiant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
