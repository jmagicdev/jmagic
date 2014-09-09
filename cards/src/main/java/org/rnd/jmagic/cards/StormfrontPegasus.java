package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Stormfront Pegasus")
@Types({Type.CREATURE})
@SubTypes({SubType.PEGASUS})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class StormfrontPegasus extends Card
{
	public StormfrontPegasus(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
