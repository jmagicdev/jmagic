package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Swamp")
@SuperTypes({SuperType.BASIC})
@Types({Type.LAND})
@SubTypes({SubType.SWAMP})
@ColorIdentity({})
public final class Swamp extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public Swamp(org.rnd.jmagic.engine.GameState state)
	{
		super(state);
	}
}
