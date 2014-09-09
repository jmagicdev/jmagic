package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Volcanic Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class VolcanicDragon extends Card
{
	public VolcanicDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
