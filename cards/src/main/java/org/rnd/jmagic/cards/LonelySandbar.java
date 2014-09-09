package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lonely Sandbar")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE})
public final class LonelySandbar extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public LonelySandbar(GameState state)
	{
		super(state, "(U)");
	}
}
