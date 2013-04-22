package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Barony Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BaronyVampire extends Card
{
	public BaronyVampire(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
