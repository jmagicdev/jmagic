package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scrubland")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.PLAINS})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Scrubland extends Card
{
	public Scrubland(GameState state)
	{
		super(state);

	}
}
