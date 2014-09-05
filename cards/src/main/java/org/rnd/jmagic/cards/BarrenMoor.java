package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Barren Moor")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BarrenMoor extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public BarrenMoor(GameState state)
	{
		super(state, "(B)");
	}
}
