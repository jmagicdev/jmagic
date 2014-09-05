package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Naya Panorama")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class NayaPanorama extends org.rnd.jmagic.cardTemplates.ShardsPanorama
{
	public NayaPanorama(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.FOREST, SubType.PLAINS);
	}
}
