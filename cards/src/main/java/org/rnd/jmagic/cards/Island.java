package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Island")
@SuperTypes({SuperType.BASIC})
@Types({Type.LAND})
@SubTypes({SubType.ISLAND})
@ColorIdentity({})
public final class Island extends org.rnd.jmagic.cardTemplates.BasicLand
{
	public Island(org.rnd.jmagic.engine.GameState state)
	{
		super(state);
	}
}
