package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Adarkar Wastes")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AdarkarWastes extends org.rnd.jmagic.cardTemplates.PainLand
{
	public AdarkarWastes(GameState state)
	{
		super(state, "WU");
	}
}
