package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cylian Elf")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CylianElf extends Card
{
	public CylianElf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
