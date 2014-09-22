package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bring Low")
@Types({Type.INSTANT})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class BringLow extends Card
{
	public BringLow(GameState state)
	{
		super(state);

		// Bring Low deals 3 damage to target creature. If that creature has a
		// +1/+1 counter on it, Bring Low deals 5 damage to it instead.

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory damage = spellDealDamage(3, target, "Bring Low deals 3 damage to target creature.");
		EventFactory moreDamage = spellDealDamage(5, target, "Bring Low deals 5 damage to target creature.");
		SetGenerator condition = Intersect.instance(target, HasCounterOfType.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
		this.addEffect(ifThenElse(condition, moreDamage, damage, "Bring Low deals 3 damage to target creature. If that creature has a +1/+1 counter on it, Bring Low deals 5 damage to it instead."));
	}
}
