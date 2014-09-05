package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Forgotten Cave")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ForgottenCave extends org.rnd.jmagic.cardTemplates.OnslaughtCyclingLand
{
	public ForgottenCave(GameState state)
	{
		super(state, "(R)");
	}
}
