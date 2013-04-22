package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shivan Reef")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class ShivanReef extends org.rnd.jmagic.cardTemplates.PainLand
{
	public ShivanReef(GameState state)
	{
		super(state, "UR");
	}
}
