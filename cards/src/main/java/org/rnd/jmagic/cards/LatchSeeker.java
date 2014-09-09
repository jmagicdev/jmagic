package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Latch Seeker")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class LatchSeeker extends Card
{
	public LatchSeeker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Latch Seeker is unblockable.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));
	}
}
