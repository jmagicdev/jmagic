package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Azorius Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class AzoriusSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public AzoriusSignet(GameState state)
	{
		super(state, 'W', 'U');
	}
}
