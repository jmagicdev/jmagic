package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Griffin Sentinel")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class GriffinSentinel extends Card
{
	public GriffinSentinel(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
