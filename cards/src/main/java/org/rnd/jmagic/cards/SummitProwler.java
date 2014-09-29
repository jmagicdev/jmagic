package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Summit Prowler")
@Types({Type.CREATURE})
@SubTypes({SubType.YETI})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class SummitProwler extends Card
{
	public SummitProwler(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
