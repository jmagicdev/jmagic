package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Badlands")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.SWAMP})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Badlands extends Card
{
	public Badlands(GameState state)
	{
		super(state);

	}
}
