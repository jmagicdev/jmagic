package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mantis Rider")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class MantisRider extends Card
{
	public MantisRider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying, vigilance, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
