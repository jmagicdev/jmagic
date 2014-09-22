package org.rnd.jmagic.cards;

import org.rnd.jmagic.cardTemplates.*;
import org.rnd.jmagic.engine.*;

@Name("Rootbound Crag")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class RootboundCrag extends Magic2010DualLand
{
	public RootboundCrag(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.FOREST);
	}
}
