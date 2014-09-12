package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Life's Legacy")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class LifesLegacy extends Card
{
	public LifesLegacy(GameState state)
	{
		super(state);

		// As an additional cost to cast Life's Legacy, sacrifice a creature.
		EventFactory cost = sacrificeACreature();
		this.addCost(cost);

		// Draw cards equal to the sacrificed creature's power.
		SetGenerator sacced = NewObjectOf.instance(CostResult.instance(cost));
		this.addEffect(drawCards(You.instance(), PowerOf.instance(sacced), "Draw cards equal to the sacrificed creature's power."));
	}
}
