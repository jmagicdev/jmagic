package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Selesnya Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public SelesnyaSignet(GameState state)
	{
		super(state, 'G', 'W');
	}
}
