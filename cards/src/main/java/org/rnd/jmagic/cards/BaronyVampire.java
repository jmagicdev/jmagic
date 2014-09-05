package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Barony Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
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
