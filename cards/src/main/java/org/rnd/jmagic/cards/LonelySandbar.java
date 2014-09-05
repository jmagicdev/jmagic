package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Lonely Sandbar")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LonelySandbar extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public LonelySandbar(GameState state)
	{
		super(state, "(U)");
	}
}
