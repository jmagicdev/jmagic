package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Llanowar Wastes")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class LlanowarWastes extends org.rnd.jmagic.cardTemplates.PainLand
{
	public LlanowarWastes(GameState state)
	{
		super(state, "BG");
	}
}
