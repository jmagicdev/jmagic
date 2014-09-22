package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Karplusan Forest")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class KarplusanForest extends org.rnd.jmagic.cardTemplates.PainLand
{
	public KarplusanForest(GameState state)
	{
		super(state, "RG");
	}
}
