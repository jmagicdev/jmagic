package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Unleash extends Keyword
{
	public Unleash(GameState state)
	{
		super(state, "Unleash");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new PutCounter(this.state));
		ret.add(new CantBlock(this.state));
		return ret;
	}

	public static final class PutCounter extends StaticAbility
	{
		public PutCounter(GameState state)
		{
			super(state, "You may have this permanent enter the battlefield with an additional +1/+1 counter on it.");

			ZoneChangeReplacementEffect putCounter = new ZoneChangeReplacementEffect(this.game, "You may have this permanent enter the battlefield with an additional +1/+1 counter on it.");
			putCounter.makeOptional(You.instance());
			putCounter.addPattern(asThisEntersTheBattlefield());
			putCounter.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, NewObjectOf.instance(putCounter.replacedByThis()), "Have this permanent enter the battlefield with an additional +1/+1 counter on it."));
			this.addEffectPart(replacementEffectPart(putCounter));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class CantBlock extends StaticAbility
	{
		public CantBlock(GameState state)
		{
			super(state, "This permanent can't block as long as it has a +1/+1 counter on it.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), This.instance())));
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, CountersOn.instance(This.instance(), Counter.CounterType.PLUS_ONE_PLUS_ONE));
		}
	}
}
