package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Silverclaw Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class SilverclawGriffin extends Card
{
	public SilverclawGriffin(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying, first strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
