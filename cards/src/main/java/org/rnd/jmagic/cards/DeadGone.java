package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dead // Gone")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class DeadGone extends SplitCard
{
	public DeadGone(GameState state)
	{
		super(state, Dead.class, Gone.class);
	}
}
