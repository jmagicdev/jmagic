package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Caves of Koilos")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class CavesofKoilos extends org.rnd.jmagic.cardTemplates.PainLand
{
	public CavesofKoilos(GameState state)
	{
		super(state, "WB");
	}
}
