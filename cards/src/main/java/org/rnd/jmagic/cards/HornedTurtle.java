package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Horned Turtle")
@Types({Type.CREATURE})
@SubTypes({SubType.TURTLE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class HornedTurtle extends Card
{
	public HornedTurtle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);
	}
}
