package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Golgari Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class GolgariSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public GolgariSignet(GameState state)
	{
		super(state, 'B', 'G');
	}
}
