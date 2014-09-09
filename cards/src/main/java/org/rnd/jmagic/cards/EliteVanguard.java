package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Elite Vanguard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class EliteVanguard extends Card
{
	public EliteVanguard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
