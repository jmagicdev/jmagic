package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wear // Tear")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = DragonsMaze.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class WearTear extends SplitCard
{
	public WearTear(GameState state)
	{
		super(state, Wear.class, Tear.class);
	}
}
