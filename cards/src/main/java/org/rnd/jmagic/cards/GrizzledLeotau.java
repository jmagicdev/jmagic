package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Grizzled Leotau")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GrizzledLeotau extends Card
{
	public GrizzledLeotau(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);
	}
}
