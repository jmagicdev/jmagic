package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Runeclaw Bear")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class RuneclawBear extends Card
{
	public RuneclawBear(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
