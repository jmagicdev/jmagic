package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Highspire Mantis")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2RW")
@ColorIdentity({Color.RED, Color.WHITE})
public final class HighspireMantis extends Card
{
	public HighspireMantis(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
