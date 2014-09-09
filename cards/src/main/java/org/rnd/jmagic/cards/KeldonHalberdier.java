package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Keldon Halberdier")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class KeldonHalberdier extends Card
{
	public KeldonHalberdier(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 4, "(R)"));
	}
}
