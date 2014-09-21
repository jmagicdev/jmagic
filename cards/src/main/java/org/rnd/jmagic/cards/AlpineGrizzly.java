package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Alpine Grizzly")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class AlpineGrizzly extends Card
{
	public AlpineGrizzly(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);
	}
}
