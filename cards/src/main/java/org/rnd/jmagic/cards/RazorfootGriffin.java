package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Razorfoot Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class RazorfootGriffin extends Card
{
	public RazorfootGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
