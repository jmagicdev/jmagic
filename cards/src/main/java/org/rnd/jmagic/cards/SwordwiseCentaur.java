package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Swordwise Centaur")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR, SubType.WARRIOR})
@ManaCost("GG")
@ColorIdentity({Color.GREEN})
public final class SwordwiseCentaur extends Card
{
	public SwordwiseCentaur(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
