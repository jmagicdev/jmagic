package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tranquil Thicket")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TranquilThicket extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public TranquilThicket(GameState state)
	{
		super(state, "(G)");
	}
}
