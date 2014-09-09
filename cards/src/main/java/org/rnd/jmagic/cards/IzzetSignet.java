package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Izzet Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public IzzetSignet(GameState state)
	{
		super(state, 'U', 'R');
	}
}
