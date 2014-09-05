package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wooded Foothills")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class WoodedFoothills extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public WoodedFoothills(GameState state)
	{
		super(state, SubType.MOUNTAIN, SubType.FOREST);
	}
}
