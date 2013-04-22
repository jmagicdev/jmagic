package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dross Crocodile")
@Types({Type.CREATURE})
@SubTypes({SubType.CROCODILE, SubType.ZOMBIE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DrossCrocodile extends Card
{
	public DrossCrocodile(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);
	}
}
