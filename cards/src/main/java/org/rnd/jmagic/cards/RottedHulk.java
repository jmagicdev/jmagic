package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rotted Hulk")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class RottedHulk extends Card
{
	public RottedHulk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);
	}
}
