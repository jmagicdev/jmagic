package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tropical Island")
@Types({Type.LAND})
@SubTypes({SubType.ISLAND, SubType.FOREST})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class TropicalIsland extends Card
{
	public TropicalIsland(GameState state)
	{
		super(state);

	}
}
