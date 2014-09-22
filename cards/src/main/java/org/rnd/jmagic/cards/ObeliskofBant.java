package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Obelisk of Bant")
@ManaCost("3")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.GREEN})
public final class ObeliskofBant extends org.rnd.jmagic.cardTemplates.ShardsObelisk
{
	public ObeliskofBant(GameState state)
	{
		super(state, "(GWU)");
	}
}
