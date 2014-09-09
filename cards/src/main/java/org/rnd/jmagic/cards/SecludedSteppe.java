package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Secluded Steppe")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE})
public final class SecludedSteppe extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public SecludedSteppe(GameState state)
	{
		super(state, "(W)");
	}
}
