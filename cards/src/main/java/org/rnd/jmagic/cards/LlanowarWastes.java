package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Llanowar Wastes")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class LlanowarWastes extends org.rnd.jmagic.cardTemplates.PainLand
{
	public LlanowarWastes(GameState state)
	{
		super(state, "BG");
	}
}
