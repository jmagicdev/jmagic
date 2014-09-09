package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ornithopter")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.THOPTER})
@ManaCost("0")
@ColorIdentity({})
public final class Ornithopter extends Card
{
	public Ornithopter(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
