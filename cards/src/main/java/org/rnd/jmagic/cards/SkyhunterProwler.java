package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skyhunter Prowler")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.KNIGHT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class SkyhunterProwler extends Card
{
	public SkyhunterProwler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
