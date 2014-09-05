package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dregscape Zombie")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DregscapeZombie extends Card
{
	public DregscapeZombie(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(B)"));
	}
}
