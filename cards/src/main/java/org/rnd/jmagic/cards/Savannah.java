package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Savannah")
@Types({Type.LAND})
@SubTypes({SubType.PLAINS, SubType.FOREST})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Savannah extends Card
{
	public Savannah(GameState state)
	{
		super(state);

	}
}
