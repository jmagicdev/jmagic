package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Arid Mesa")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class AridMesa extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public AridMesa(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.PLAINS);
	}
}
