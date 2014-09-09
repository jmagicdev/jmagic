package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Jungle Shrine")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class JungleShrine extends org.rnd.jmagic.cardTemplates.ETBTLand
{
	public JungleShrine(GameState state)
	{
		super(state, "(RGW)");
	}
}
