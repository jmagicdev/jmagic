package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Grixis Panorama")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({})
public final class GrixisPanorama extends ShardsPanorama
{
	public GrixisPanorama(GameState state)
	{
		super(state, SubType.ISLAND, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
