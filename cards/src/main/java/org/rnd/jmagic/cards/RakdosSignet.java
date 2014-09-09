package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rakdos Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public RakdosSignet(GameState state)
	{
		super(state, 'B', 'R');
	}
}
