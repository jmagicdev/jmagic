package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sulfurous Springs")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED})
public final class SulfurousSprings extends org.rnd.jmagic.cardTemplates.PainLand
{
	public SulfurousSprings(GameState state)
	{
		super(state, "BR");
	}
}
