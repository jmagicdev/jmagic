package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Misty Rainforest")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({})
public final class MistyRainforest extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public MistyRainforest(GameState state)
	{
		super(state, SubType.FOREST, SubType.ISLAND);
	}
}
