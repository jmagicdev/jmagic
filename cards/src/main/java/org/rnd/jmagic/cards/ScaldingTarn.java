package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scalding Tarn")
@Types({Type.LAND})
@ColorIdentity({})
public final class ScaldingTarn extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public ScaldingTarn(GameState state)
	{
		super(state, SubType.ISLAND, SubType.MOUNTAIN);
	}
}
