package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Grixis Panorama")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class GrixisPanorama extends ShardsPanorama
{
	public GrixisPanorama(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
