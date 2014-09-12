package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bronze Sable")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.SABLE})
@ManaCost("2")
@ColorIdentity({})
public final class BronzeSable extends Card
{
	public BronzeSable(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
