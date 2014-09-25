package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dragon-Style Twins")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK})
@ManaCost("3RR")
@ColorIdentity({Color.RED})
public final class DragonStyleTwins extends Card
{
	public DragonStyleTwins(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Double strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
