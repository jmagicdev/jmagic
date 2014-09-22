package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rhox Brute")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.RHINO})
@ManaCost("2RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class RhoxBrute extends Card
{
	public RhoxBrute(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);
	}
}
