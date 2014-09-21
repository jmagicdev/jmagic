package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Alabaster Kirin")
@Types({Type.CREATURE})
@SubTypes({SubType.KIRIN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class AlabasterKirin extends Card
{
	public AlabasterKirin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying, vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
