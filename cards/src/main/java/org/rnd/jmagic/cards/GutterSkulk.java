package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gutter Skulk")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT, SubType.ZOMBIE})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class GutterSkulk extends Card
{
	public GutterSkulk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
