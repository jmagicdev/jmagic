package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kindercatch")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3GGG")
@ColorIdentity({Color.GREEN})
public final class Kindercatch extends Card
{
	public Kindercatch(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);
	}
}
