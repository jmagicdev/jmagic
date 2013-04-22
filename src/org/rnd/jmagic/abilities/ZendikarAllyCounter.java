package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class ZendikarAllyCounter extends EventTriggeredAbility
{
	private String thisName;

	public ZendikarAllyCounter(GameState state, String thisName)
	{
		super(state, "Whenever " + thisName + " or another Ally enters the battlefield under your control, you may put a +1/+1 counter on " + thisName + ".");

		this.thisName = thisName;

		this.addPattern(allyTrigger());
		EventFactory counterFactory = putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, thisName);
		this.addEffect(youMay(counterFactory, "You may put a +1/+1 counter on " + thisName + "."));
	}

	@Override
	public ZendikarAllyCounter create(Game game)
	{
		return new ZendikarAllyCounter(game.physicalState, this.thisName);
	}
}
