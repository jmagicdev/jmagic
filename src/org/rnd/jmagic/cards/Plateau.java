package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plateau")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.PLAINS})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Plateau extends Card
{
	public Plateau(GameState state)
	{
		super(state);

	}
}
