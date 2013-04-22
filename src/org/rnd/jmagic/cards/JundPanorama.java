package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Jund Panorama")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({})
public final class JundPanorama extends ShardsPanorama
{
	public JundPanorama(GameState state)
	{
		super(state, SubType.SWAMP, SubType.MOUNTAIN, SubType.FOREST);
	}
}
