package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skyhunter Patrol")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.KNIGHT})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class SkyhunterPatrol extends Card
{
	public SkyhunterPatrol(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
