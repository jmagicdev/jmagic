package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fortress Crab")
@Types({Type.CREATURE})
@SubTypes({SubType.CRAB})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class FortressCrab extends Card
{
	public FortressCrab(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(6);
	}
}
