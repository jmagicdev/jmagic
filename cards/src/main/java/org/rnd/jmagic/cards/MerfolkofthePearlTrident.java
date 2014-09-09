package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Merfolk of the Pearl Trident")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class MerfolkofthePearlTrident extends Card
{
	public MerfolkofthePearlTrident(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
	}
}
