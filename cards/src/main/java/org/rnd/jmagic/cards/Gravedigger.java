package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gravedigger")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class Gravedigger extends Card
{
	public Gravedigger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.Gravedigging(state, "Gravedigger"));
	}
}
