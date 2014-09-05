package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Esper Panorama")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class EsperPanorama extends ShardsPanorama
{
	public EsperPanorama(GameState state)
	{
		super(state, SubType.PLAINS, SubType.ISLAND, SubType.SWAMP);
	}
}
