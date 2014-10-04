package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pensive Minotaur")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.WARRIOR})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class PensiveMinotaur extends Card
{
	public PensiveMinotaur(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
