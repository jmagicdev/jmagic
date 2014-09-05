package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Warpath Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class WarpathGhoul extends Card
{
	public WarpathGhoul(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
