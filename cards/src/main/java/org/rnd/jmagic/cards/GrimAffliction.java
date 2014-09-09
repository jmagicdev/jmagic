package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Affliction")
@Types({Type.INSTANT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class GrimAffliction extends Card
{
	public GrimAffliction(GameState state)
	{
		super(state);

		// Put a -1/-1 counter on target creature, then proliferate. (You choose
		// any number of permanents and/or players with counters on them, then
		// give each another counter of a kind already there.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature,"));
		this.addEffect(proliferate(You.instance(), "then proliferate."));
	}
}
