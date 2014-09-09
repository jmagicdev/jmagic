package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gruul Signet")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GruulSignet extends org.rnd.jmagic.cardTemplates.Signet
{
	public GruulSignet(GameState state)
	{
		super(state, 'R', 'G');
	}
}
