package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Glory Seeker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class GlorySeeker extends Card
{
	public GlorySeeker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
