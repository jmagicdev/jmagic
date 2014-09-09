package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vastwood Gorger")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class VastwoodGorger extends Card
{
	public VastwoodGorger(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);
	}
}
