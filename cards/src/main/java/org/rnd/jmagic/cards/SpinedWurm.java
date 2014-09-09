package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spined Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class SpinedWurm extends Card
{
	public SpinedWurm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
