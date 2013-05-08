package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burst of Strength")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BurstofStrength extends Card
{
	public BurstofStrength(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on target creature and untap it.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature"));
		this.addEffect(untap(target, "and untap it."));
	}
}
