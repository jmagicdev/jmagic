package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Roughrider")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.GOBLIN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class GoblinRoughrider extends Card
{
	public GoblinRoughrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
