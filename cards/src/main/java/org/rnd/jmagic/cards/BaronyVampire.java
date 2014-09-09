package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Barony Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class BaronyVampire extends Card
{
	public BaronyVampire(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
