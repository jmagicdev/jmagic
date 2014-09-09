package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Azure Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class AzureDrake extends Card
{
	public AzureDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
