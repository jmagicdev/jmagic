package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gloomhunter")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class Gloomhunter extends Card
{
	public Gloomhunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
