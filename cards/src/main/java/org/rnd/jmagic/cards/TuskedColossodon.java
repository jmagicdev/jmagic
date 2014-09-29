package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tusked Colossodon")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class TuskedColossodon extends Card
{
	public TuskedColossodon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);
	}
}
