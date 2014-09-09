package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Emerald Medallion")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({})
public final class EmeraldMedallion extends org.rnd.jmagic.cardTemplates.Medallion
{
	public EmeraldMedallion(GameState state)
	{
		super(state, Color.GREEN);
	}
}
