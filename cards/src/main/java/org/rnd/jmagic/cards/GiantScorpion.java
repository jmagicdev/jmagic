package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Giant Scorpion")
@Types({Type.CREATURE})
@SubTypes({SubType.SCORPION})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class GiantScorpion extends Card
{
	public GiantScorpion(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
