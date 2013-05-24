package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Underground Sea")
@Types({Type.LAND})
@SubTypes({SubType.SWAMP, SubType.ISLAND})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class UndergroundSea extends Card
{
	public UndergroundSea(GameState state)
	{
		super(state);

	}
}
