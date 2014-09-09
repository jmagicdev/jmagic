package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wear // Tear")
@Types({Type.INSTANT})
@ColorIdentity({Color.WHITE, Color.RED})
public final class WearTear extends SplitCard
{
	public WearTear(GameState state)
	{
		super(state, Wear.class, Tear.class);
	}
}
