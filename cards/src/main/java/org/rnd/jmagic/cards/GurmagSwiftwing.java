package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gurmag Swiftwing")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class GurmagSwiftwing extends Card
{
	public GurmagSwiftwing(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying, first strike, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
