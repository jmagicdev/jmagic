package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodstained Mire")
@Types({Type.LAND})
@ColorIdentity({})
public final class BloodstainedMire extends org.rnd.jmagic.cardTemplates.FetchLand
{
	public BloodstainedMire(GameState state)
	{
		super(state, SubType.SWAMP, SubType.MOUNTAIN);
	}
}
