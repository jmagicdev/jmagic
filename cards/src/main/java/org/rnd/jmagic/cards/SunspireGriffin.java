package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sunspire Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class SunspireGriffin extends Card
{
	public SunspireGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
