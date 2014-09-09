package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wind Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class WindDrake extends Card
{
	public WindDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
