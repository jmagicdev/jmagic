package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Obelisk of Esper")
@ManaCost("3")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class ObeliskofEsper extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofEsper(GameState state)
	{
		super(state, "(WUB)");
	}
}
