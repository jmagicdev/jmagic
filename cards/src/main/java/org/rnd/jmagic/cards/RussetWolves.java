package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Russet Wolves")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RussetWolves extends Card
{
	public RussetWolves(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
	}
}
