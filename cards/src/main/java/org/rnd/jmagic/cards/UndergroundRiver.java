package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Underground River")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class UndergroundRiver extends org.rnd.jmagic.cardTemplates.PainLand
{
	public UndergroundRiver(GameState state)
	{
		super(state, "UB");
	}
}
