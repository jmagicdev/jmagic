package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Battering Craghorn")
@Types({Type.CREATURE})
@SubTypes({SubType.GOAT, SubType.BEAST})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class BatteringCraghorn extends Card
{
	public BatteringCraghorn(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Morph (1)(R)(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(1)(R)(R)"));
	}
}
