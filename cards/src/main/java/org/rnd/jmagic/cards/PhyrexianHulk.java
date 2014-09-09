package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phyrexian Hulk")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@ColorIdentity({})
public final class PhyrexianHulk extends Card
{
	public PhyrexianHulk(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
