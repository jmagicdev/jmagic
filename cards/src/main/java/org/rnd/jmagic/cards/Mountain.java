package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mountain")
@SuperTypes({SuperType.BASIC})
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN})
@ColorIdentity({})
public final class Mountain extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public Mountain(org.rnd.jmagic.engine.GameState state)
	{
		super(state);
	}
}
