package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Underground River")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class UndergroundRiver extends org.rnd.jmagic.cardTemplates.PainLand
{
	public UndergroundRiver(GameState state)
	{
		super(state, "UB");
	}
}
