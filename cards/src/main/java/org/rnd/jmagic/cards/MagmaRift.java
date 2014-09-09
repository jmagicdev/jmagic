package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magma Rift")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class MagmaRift extends Card
{
	public MagmaRift(GameState state)
	{
		super(state);

		// As an additional cost to cast Magma Rift, sacrifice a land.
		this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));

		// Magma Rift deals 5 damage to target creature.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(5, targetedBy(target), "Magma Rift deals 5 damage to target creature."));
	}
}
