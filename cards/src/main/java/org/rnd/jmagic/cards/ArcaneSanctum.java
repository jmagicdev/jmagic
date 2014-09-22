package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Arcane Sanctum")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class ArcaneSanctum extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public ArcaneSanctum(GameState state)
	{
		super(state, "(WUB)");
	}
}
