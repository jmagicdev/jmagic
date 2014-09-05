package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Shivan Reef")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Apocalypse.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class ShivanReef extends org.rnd.jmagic.cardTemplates.PainLand
{
	public ShivanReef(GameState state)
	{
		super(state, "UR");
	}
}
