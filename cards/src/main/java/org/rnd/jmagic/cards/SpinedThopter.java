package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spined Thopter")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.THOPTER})
@ManaCost("2(U/P)")
@ColorIdentity({Color.BLUE})
public final class SpinedThopter extends Card
{
	public SpinedThopter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
