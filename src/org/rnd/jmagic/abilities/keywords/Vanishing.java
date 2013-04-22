package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Vanishing extends Keyword
{
	private final int N;

	public Vanishing(GameState state)
	{
		this(state, 0);
	}

	public Vanishing(GameState state, int N)
	{
		super(state, "Vanishing " + N);
		this.N = N;
	}

	@Override
	public Vanishing create(Game game)
	{
		return new Vanishing(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		if(this.N == 0)
			return super.createStaticAbilities();
		return java.util.Collections.<StaticAbility>singletonList(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(this.state, "This permanent", this.N, Counter.CounterType.TIME));
	}

	public static final class RemoveCounter extends EventTriggeredAbility
	{
		public RemoveCounter(GameState state)
		{
			super(state, "At the beginning of your upkeep, if this permanent has a time counter on it, remove a time counter from it.");

			// At the beginning of your upkeep,
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			// if this permanent has a time counter on it,
			SetGenerator numTimeCounters = Count.instance(CountersOn.instance(thisCard, Counter.CounterType.TIME));
			SetGenerator hasCounter = Intersect.instance(Between.instance(1, null), numTimeCounters);
			this.interveningIf = hasCounter;

			// remove a time counter from it.
			this.addEffect(removeCountersFromThis(1, Counter.CounterType.TIME, "this card"));
		}
	}

	public static final class KillMe extends EventTriggeredAbility
	{
		public KillMe(GameState state)
		{
			super(state, "When the last time counter is removed from this permanent, sacrifice it.");
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			// When the last time counter is removed from this permanent
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.REMOVED_LAST_COUNTER);
			pattern.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.TIME));
			pattern.put(EventType.Parameter.OBJECT, thisCard);
			this.addPattern(pattern);

			// Sacrifice it
			this.addEffect(sacrificeThis("this permanent"));
		}
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new RemoveCounter(this.state));
		ret.add(new KillMe(this.state));

		return ret;
	}
}
