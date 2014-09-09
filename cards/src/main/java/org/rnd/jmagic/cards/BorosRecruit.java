package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Boros Recruit")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.GOBLIN})
@ManaCost("(R/W)")
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosRecruit extends Card
{
	public BorosRecruit(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
