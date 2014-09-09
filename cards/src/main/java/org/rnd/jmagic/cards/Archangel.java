package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Archangel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class Archangel extends Card
{
	public Archangel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
