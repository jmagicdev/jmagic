package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Brushland")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class Brushland extends org.rnd.jmagic.cardTemplates.PainLand
{
	public Brushland(GameState state)
	{
		super(state, "GW");
	}
}
