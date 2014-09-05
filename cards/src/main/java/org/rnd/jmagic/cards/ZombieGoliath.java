package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Zombie Goliath")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ZombieGoliath extends Card
{
	public ZombieGoliath(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);
	}
}
