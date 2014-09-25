package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dragonscale Boon")
@Types({Type.INSTANT})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class DragonscaleBoon extends Card
{
	public DragonscaleBoon(GameState state)
	{
		super(state);

		// Put two +1/+1 counters on target creature and untap it.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put two +1/+1 counters on target creature"));
		this.addEffect(untap(target, "and untap it."));
	}
}
