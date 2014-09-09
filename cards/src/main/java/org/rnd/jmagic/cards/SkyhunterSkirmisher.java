package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skyhunter Skirmisher")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.KNIGHT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class SkyhunterSkirmisher extends Card
{
	public SkyhunterSkirmisher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));
	}
}
