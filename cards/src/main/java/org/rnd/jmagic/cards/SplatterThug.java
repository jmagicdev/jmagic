package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Splatter Thug")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class SplatterThug extends Card
{
	public SplatterThug(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
