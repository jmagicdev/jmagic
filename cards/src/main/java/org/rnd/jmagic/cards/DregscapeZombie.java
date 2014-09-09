package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dregscape Zombie")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
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
