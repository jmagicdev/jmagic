package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Leonin Skyhunter")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.KNIGHT})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class LeoninSkyhunter extends Card
{
	public LeoninSkyhunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying (This creature can't be blocked except by creatures with
		// flying or reach.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
