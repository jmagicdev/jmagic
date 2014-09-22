package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Seaside Citadel")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.GREEN})
public final class SeasideCitadel extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public SeasideCitadel(GameState state)
	{
		super(state, "(GWU)");
	}
}
