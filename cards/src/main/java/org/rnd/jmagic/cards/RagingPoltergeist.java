package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Raging Poltergeist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class RagingPoltergeist extends Card
{
	public RagingPoltergeist(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(1);
	}
}
