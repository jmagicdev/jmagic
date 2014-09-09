package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Suntail Hawk")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class SuntailHawk extends Card
{
	public SuntailHawk(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
