package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Fungal Reaches")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FungalReaches extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public FungalReaches(GameState state)
	{
		super(state, "RG");
	}
}
