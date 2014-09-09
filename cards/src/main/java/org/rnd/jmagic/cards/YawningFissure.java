package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Yawning Fissure")
@Types({Type.SORCERY})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class YawningFissure extends Card
{
	public YawningFissure(GameState state)
	{
		super(state);

		this.addCost(sacrifice(OpponentsOf.instance(You.instance()), 1, LandPermanents.instance(), "Each opponent sacrifices a land."));
	}
}
