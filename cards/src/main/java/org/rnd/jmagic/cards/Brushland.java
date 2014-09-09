package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Brushland")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class Brushland extends org.rnd.jmagic.cardTemplates.PainLand
{
	public Brushland(GameState state)
	{
		super(state, "GW");
	}
}
