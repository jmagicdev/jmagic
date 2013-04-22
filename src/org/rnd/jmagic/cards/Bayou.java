package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bayou")
@Types({Type.LAND})
@SubTypes({SubType.FOREST, SubType.SWAMP})
@Printings({@Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class Bayou extends Card
{
	public Bayou(GameState state)
	{
		super(state);

	}
}
