package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dead // Gone")
@Types({Type.INSTANT})
@ColorIdentity({Color.RED})
public final class DeadGone extends SplitCard
{
	public DeadGone(GameState state)
	{
		super(state, Dead.class, Gone.class);
	}
}
