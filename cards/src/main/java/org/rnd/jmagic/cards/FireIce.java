package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fire // Ice")
@Types({Type.INSTANT})
@ColorIdentity({Color.BLUE, Color.RED})
public final class FireIce extends SplitCard
{
	public FireIce(GameState state)
	{
		super(state, Fire.class, Ice.class);
	}
}
