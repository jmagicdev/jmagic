package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Taiga")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.FOREST})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Taiga extends Card
{
	public Taiga(GameState state)
	{
		super(state);

	}
}
