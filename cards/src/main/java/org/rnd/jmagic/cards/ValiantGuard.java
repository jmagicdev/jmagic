package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Valiant Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class ValiantGuard extends Card
{
	public ValiantGuard(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);
	}
}
