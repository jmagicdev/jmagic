package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sulfurous Springs")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class SulfurousSprings extends org.rnd.jmagic.cardTemplates.PainLand
{
	public SulfurousSprings(GameState state)
	{
		super(state, "BR");
	}
}
