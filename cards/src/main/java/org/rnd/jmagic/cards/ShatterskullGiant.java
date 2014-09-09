package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shatterskull Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.WARRIOR})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class ShatterskullGiant extends Card
{
	public ShatterskullGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
