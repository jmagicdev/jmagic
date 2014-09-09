package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Concordia Pegasus")
@Types({Type.CREATURE})
@SubTypes({SubType.PEGASUS})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class ConcordiaPegasus extends Card
{
	public ConcordiaPegasus(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
