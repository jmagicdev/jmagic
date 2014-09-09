package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lumengrid Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class LumengridWarden extends Card
{
	public LumengridWarden(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);
	}
}
