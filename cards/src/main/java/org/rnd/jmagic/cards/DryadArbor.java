package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dryad Arbor")
@Types({Type.LAND, Type.CREATURE})
@SubTypes({SubType.FOREST, SubType.DRYAD})
@ColorIdentity({Color.GREEN})
public final class DryadArbor extends Card
{
	public DryadArbor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.setColorIndicator(Color.GREEN);
	}
}
