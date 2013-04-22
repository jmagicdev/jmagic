package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Windswept Heath")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({})
public final class WindsweptHeath extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public WindsweptHeath(GameState state)
	{
		super(state, SubType.FOREST, SubType.PLAINS);
	}
}
