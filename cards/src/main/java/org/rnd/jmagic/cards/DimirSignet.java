package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dimir Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public DimirSignet(GameState state)
	{
		super(state, 'U', 'B');
	}
}
