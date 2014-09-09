package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Marsh Flats")
@Types({Type.LAND})
@ColorIdentity({})
public final class MarshFlats extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public MarshFlats(GameState state)
	{
		super(state, SubType.PLAINS, SubType.SWAMP);
	}
}
