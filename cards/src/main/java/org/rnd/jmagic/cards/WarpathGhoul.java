package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Warpath Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class WarpathGhoul extends Card
{
	public WarpathGhoul(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
