package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Volcanic Island")
@Types({Type.LAND})
@SubTypes({SubType.MOUNTAIN, SubType.ISLAND})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE)})
@ColorIdentity({})
public final class VolcanicIsland extends Card
{
	public VolcanicIsland(GameState state)
	{
		super(state);

	}
}
