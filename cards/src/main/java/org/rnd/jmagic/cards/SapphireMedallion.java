package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sapphire Medallion")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({})
public final class SapphireMedallion extends org.rnd.jmagic.cardTemplates.Medallion
{
	public SapphireMedallion(GameState state)
	{
		super(state, Color.BLUE);
	}
}
