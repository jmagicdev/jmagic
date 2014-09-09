package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Grizzly Bears")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class GrizzlyBears extends Card
{
	public GrizzlyBears(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
