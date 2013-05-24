package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zombie Goliath")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
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
