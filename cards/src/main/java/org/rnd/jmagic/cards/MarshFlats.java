package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Marsh Flats")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class MarshFlats extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public MarshFlats(GameState state)
	{
		super(state, SubType.PLAINS, SubType.SWAMP);
	}
}
