package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Piker")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class GoblinPiker extends Card
{
	public GoblinPiker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
