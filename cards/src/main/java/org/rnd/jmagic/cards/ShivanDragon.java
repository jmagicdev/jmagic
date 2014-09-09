package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shivan Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class ShivanDragon extends Card
{
	public ShivanDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
