package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bonebreaker Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class BonebreakerGiant extends Card
{
	public BonebreakerGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);
	}
}
