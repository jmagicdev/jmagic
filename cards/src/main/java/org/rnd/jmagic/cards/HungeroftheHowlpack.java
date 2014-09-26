package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunger of the Howlpack")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class HungeroftheHowlpack extends Card
{
	public HungeroftheHowlpack(GameState state)
	{
		super(state);

		// Put a +1/+1 counter on target creature.

		// Morbid \u2014 Put three +1/+1 counters on that creature instead if a
		// creature died this turn.
		SetGenerator amount = IfThenElse.instance(Morbid.instance(), numberGenerator(3), numberGenerator(1));
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(putCounters(amount, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature.\n\nMorbid \u2014 Put three +1/+1 counters on that creature instead if a creature died this turn."));

		state.ensureTracker(new Morbid.Tracker());
	}
}
