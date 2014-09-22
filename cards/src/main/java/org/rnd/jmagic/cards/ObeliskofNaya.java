package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Obelisk of Naya")
@ManaCost("3")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.RED, Color.GREEN})
public final class ObeliskofNaya extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofNaya(GameState state)
	{
		super(state, "(RGW)");
	}
}
