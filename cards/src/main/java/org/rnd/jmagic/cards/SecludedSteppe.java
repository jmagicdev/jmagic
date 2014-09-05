package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Secluded Steppe")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SecludedSteppe extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public SecludedSteppe(GameState state)
	{
		super(state, "(W)");
	}
}
