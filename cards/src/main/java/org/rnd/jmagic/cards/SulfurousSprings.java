package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sulfurous Springs")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class SulfurousSprings extends org.rnd.jmagic.cardTemplates.PainLand
{
	public SulfurousSprings(GameState state)
	{
		super(state, "BR");
	}
}
