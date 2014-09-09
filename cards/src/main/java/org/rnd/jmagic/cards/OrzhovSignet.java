package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Orzhov Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public OrzhovSignet(GameState state)
	{
		super(state, 'W', 'B');
	}
}
