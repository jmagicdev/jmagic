package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Yavimaya Coast")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Apocalypse.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class YavimayaCoast extends org.rnd.jmagic.cardTemplates.PainLand
{
	public YavimayaCoast(GameState state)
	{
		super(state, "GU");
	}
}
