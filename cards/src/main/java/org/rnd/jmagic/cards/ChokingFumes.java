package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Choking Fumes")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class ChokingFumes extends Card
{
	public ChokingFumes(GameState state)
	{
		super(state);

		// Put a -1/-1 counter on each attacking creature.
		this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, Attacking.instance(), "Put a -1/-1 counter on each attacking creature."));
	}
}
