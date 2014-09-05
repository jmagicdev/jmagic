package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Bloodstained Mire")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class BloodstainedMire extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public BloodstainedMire(GameState state)
	{
		super(state, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
