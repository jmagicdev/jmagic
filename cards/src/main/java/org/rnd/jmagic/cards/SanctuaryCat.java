package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sanctuary Cat")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class SanctuaryCat extends Card
{
	public SanctuaryCat(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);
	}
}
