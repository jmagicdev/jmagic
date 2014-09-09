package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Caves of Koilos")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class CavesofKoilos extends org.rnd.jmagic.cardTemplates.PainLand
{
	public CavesofKoilos(GameState state)
	{
		super(state, "WB");
	}
}
