package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Jhessian Lookout")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class JhessianLookout extends Card
{
	public JhessianLookout(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);
	}
}
