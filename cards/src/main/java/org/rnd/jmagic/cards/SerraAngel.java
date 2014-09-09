package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Serra Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class SerraAngel extends Card
{
	public SerraAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
