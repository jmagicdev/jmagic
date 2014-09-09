package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Renegade Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class RenegadeDemon extends Card
{
	public RenegadeDemon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);
	}
}
