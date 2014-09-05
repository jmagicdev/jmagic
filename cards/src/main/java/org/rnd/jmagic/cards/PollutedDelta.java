package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Polluted Delta")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class PollutedDelta extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public PollutedDelta(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP);
	}
}
