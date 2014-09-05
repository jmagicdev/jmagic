package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Brushland")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class Brushland extends org.rnd.jmagic.cardTemplates.PainLand
{
	public Brushland(GameState state)
	{
		super(state, "GW");
	}
}
