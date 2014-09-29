package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wetland Sambar")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class WetlandSambar extends Card
{
	public WetlandSambar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
