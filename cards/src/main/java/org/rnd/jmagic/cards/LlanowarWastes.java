package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Llanowar Wastes")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Apocalypse.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class LlanowarWastes extends org.rnd.jmagic.cardTemplates.PainLand
{
	public LlanowarWastes(GameState state)
	{
		super(state, "BG");
	}
}
