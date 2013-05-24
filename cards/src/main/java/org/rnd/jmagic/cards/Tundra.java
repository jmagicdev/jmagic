package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tundra")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.ISLAND})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Tundra extends Card
{
	public Tundra(GameState state)
	{
		super(state);

	}
}
