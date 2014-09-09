package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Canyon Minotaur")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.MINOTAUR})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class CanyonMinotaur extends Card
{
	public CanyonMinotaur(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
