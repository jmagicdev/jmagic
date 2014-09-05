package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Verdant Catacombs")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class VerdantCatacombs extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public VerdantCatacombs(GameState state)
	{
		super(state, SubType.SWAMP, SubType.FOREST);
	}
}
