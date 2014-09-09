package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ridge Rannet")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5RR")
@ColorIdentity({Color.RED})
public final class RidgeRannet extends Card
{
	public RidgeRannet(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)"));
	}
}
