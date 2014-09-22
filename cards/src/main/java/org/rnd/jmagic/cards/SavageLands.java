package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Savage Lands")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class SavageLands extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public SavageLands(GameState state)
	{
		super(state, "(BRG)");
	}
}
