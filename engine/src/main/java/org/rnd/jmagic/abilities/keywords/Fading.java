package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class Fading extends Keyword
{
	private final int N;

	public Fading(GameState state)
	{
		this(state, 0);
	}

	public Fading(GameState state, int N)
	{
		super(state, "Fading " + N);
		this.N = N;
	}

	@Override
	public Fading create(Game game)
	{
		return new Fading(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		if(this.N == 0)
			return super.createStaticAbilities();
		return java.util.Collections.<StaticAbility>singletonList(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(this.state, "This permanent", this.N, Counter.CounterType.FADE));
	}

	public static final class RemoveCounter extends EventTriggeredAbility
	{
		public RemoveCounter(GameState state)
		{
			super(state, "At the beginning of your upkeep, remove a fade counter from this permanent. If you can't, sacrifice the permanent.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory removeCounter = removeCountersFromThis(1, Counter.CounterType.FADE, "this permanent");
			EventFactory sacrifice = sacrificeThis("this permanent");

			this.addEffect(ifThenElse(removeCounter, null, sacrifice, "Remove a fade counter from this permanent. If you can't, sacrifice the permanent."));
		}
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new RemoveCounter(this.state));
	}
}
