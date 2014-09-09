package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thraben Purebloods")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class ThrabenPurebloods extends Card
{
	public ThrabenPurebloods(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);
	}
}
