package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Polluted Delta")
@Types({Type.LAND})
@ColorIdentity({})
public final class PollutedDelta extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public PollutedDelta(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP);
	}
}
