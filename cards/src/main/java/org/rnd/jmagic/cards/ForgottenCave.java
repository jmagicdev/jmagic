package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Forgotten Cave")
@Types({Type.LAND})
@ColorIdentity({Color.RED})
public final class ForgottenCave extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public ForgottenCave(GameState state)
	{
		super(state, "(R)");
	}
}
