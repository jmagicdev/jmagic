package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Yavimaya Coast")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class YavimayaCoast extends org.rnd.jmagic.cardTemplates.PainLand
{
	public YavimayaCoast(GameState state)
	{
		super(state, "GU");
	}
}
