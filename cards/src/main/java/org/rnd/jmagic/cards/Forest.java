package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Forest")
@SuperTypes({SuperType.BASIC})
@Types({Type.LAND})
@SubTypes({SubType.FOREST})
@ColorIdentity({})
public final class Forest extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public Forest(org.rnd.jmagic.engine.GameState state)
	{
		super(state);
	}
}
