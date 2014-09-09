package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Armored Cancrix")
@Types({Type.CREATURE})
@SubTypes({SubType.CRAB})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class ArmoredCancrix extends Card
{
	public ArmoredCancrix(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);
	}
}
