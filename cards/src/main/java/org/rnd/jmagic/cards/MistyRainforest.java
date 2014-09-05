package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Misty Rainforest")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class MistyRainforest extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public MistyRainforest(GameState state)
	{
		super(state, SubType.FOREST, SubType.ISLAND);
	}
}
