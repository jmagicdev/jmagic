package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Obelisk of Jund")
@ManaCost("3")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class ObeliskofJund extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofJund(GameState state)
	{
		super(state, "(BRG)");
	}
}
