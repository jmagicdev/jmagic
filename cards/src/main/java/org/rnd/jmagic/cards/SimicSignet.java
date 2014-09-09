package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Simic Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public SimicSignet(GameState state)
	{
		super(state, 'G', 'U');
	}
}
