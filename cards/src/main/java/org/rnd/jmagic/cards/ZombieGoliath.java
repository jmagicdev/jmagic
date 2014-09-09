package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zombie Goliath")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT, SubType.ZOMBIE})
@ManaCost("4B")
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
