package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Riot Devils")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class RiotDevils extends Card
{
	public RiotDevils(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
