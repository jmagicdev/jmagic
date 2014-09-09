package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plated Seastrider")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class PlatedSeastrider extends Card
{
	public PlatedSeastrider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);
	}
}
