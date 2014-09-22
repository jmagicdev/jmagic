package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Woolly Thoctar")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("RGW")
@ColorIdentity({Color.WHITE, Color.RED, Color.GREEN})
public final class WoollyThoctar extends Card
{
	public WoollyThoctar(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
