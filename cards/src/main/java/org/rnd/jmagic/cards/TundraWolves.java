package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tundra Wolves")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class TundraWolves extends Card
{
	public TundraWolves(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
