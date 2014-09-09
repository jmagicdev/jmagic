package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Coral Merfolk")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class CoralMerfolk extends Card
{
	public CoralMerfolk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
