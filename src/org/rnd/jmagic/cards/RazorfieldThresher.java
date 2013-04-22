package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Razorfield Thresher")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RazorfieldThresher extends Card
{
	public RazorfieldThresher(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);
	}
}
