package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dreadship Reef")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DreadshipReef extends org.rnd.jmagic.cardTemplates.TimeSpiralStorageLand
{
	public DreadshipReef(GameState state)
	{
		super(state, "UB");
	}
}
