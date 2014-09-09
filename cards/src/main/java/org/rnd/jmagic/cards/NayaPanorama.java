package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Naya Panorama")
@Types({Type.LAND})
@ColorIdentity({})
public final class NayaPanorama extends org.rnd.jmagic.cardTemplates.ShardsPanorama
{
	public NayaPanorama(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.FOREST, SubType.PLAINS);
	}
}
