package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Jet Medallion")
@ManaCost("2")
@Types({Type.ARTIFACT})
@ColorIdentity({})
public final class JetMedallion extends org.rnd.jmagic.cardTemplates.Medallion
{
	public JetMedallion(GameState state)
	{
		super(state, Color.BLACK);
	}
}
