package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plains")
@SuperTypes({SuperType.BASIC})
@Types({Type.LAND})
@SubTypes({SubType.PLAINS})
@ColorIdentity({})
public final class Plains extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public Plains(org.rnd.jmagic.engine.GameState state)
	{
		super(state);
	}
}
