package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Prodigal Pyromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class ProdigalPyromancer extends Card
{
	public ProdigalPyromancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.Ping(state, this.getName()));
	}
}
