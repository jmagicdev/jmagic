package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Esper Panorama")
@Types({Type.LAND})
@ColorIdentity({})
public final class EsperPanorama extends ShardsPanorama
{
	public EsperPanorama(GameState state)
	{
		super(state, SubType.PLAINS, SubType.ISLAND, SubType.SWAMP);
	}
}
