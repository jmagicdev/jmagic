package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Incremental Growth")
@Types({Type.SORCERY})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class IncrementalGrowth extends Card
{
	public IncrementalGrowth(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on target creature,
		Target firstTarget = this.addTarget(CreaturePermanents.instance(), "target creature");
		firstTarget.restrictFromLaterTargets = true;
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(firstTarget), "Put a +1/+1 counter on target creature,"));

		// two +1/+1 counters on another target creature,
		Target secondTarget = this.addTarget(CreaturePermanents.instance(), "another target creature");
		secondTarget.restrictFromLaterTargets = true;
		this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(secondTarget), "two +1/+1 counters on another target creature,"));

		// and three +1/+1 counters on a third target creature.
		SetGenerator thirdTarget = targetedBy(this.addTarget(CreaturePermanents.instance(), "a third target creature"));
		this.addEffect(putCounters(3, Counter.CounterType.PLUS_ONE_PLUS_ONE, thirdTarget, "and three +1/+1 counters on a third target creature."));
	}
}
