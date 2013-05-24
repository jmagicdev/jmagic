package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Graft extends Keyword
{
	// 702.55. Graft
	//
	// 702.55a Graft represents both a static ability and a triggered ability.
	// "Graft N" means "This permanent enters the battlefield with N +1/+1
	// counters on it" and "Whenever another creature enters the battlefield,
	// if this permanent has a +1/+1 counter on it, you may move a +1/+1
	// counter from this permanent onto that creature."
	//
	// 702.55b If a creature has multiple instances of graft, each one works
	// separately.

	private final int N;

	public Graft(GameState state, int N)
	{
		super(state, "Graft " + N);
		this.N = N;
	}

	@Override
	public Graft create(Game game)
	{
		return new Graft(game.physicalState, this.N);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();

		ret.add(new GraftTriggered(this.state));

		return ret;
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();

		ret.add(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(this.state, "This permanent", this.N, Counter.CounterType.PLUS_ONE_PLUS_ONE));

		return ret;
	}

	public static final class GraftTriggered extends EventTriggeredAbility
	{
		public GraftTriggered(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield, if this permanent has a +1/+1 counter on it, you may move a +1/+1 counter from this permanent onto that creature");

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), anotherCreature, false));

			SetGenerator plusCountersOnThis = CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE);
			this.interveningIf = plusCountersOnThis;

			// Do this intersect to make sure the result is actually another
			// creature
			SetGenerator triggerEvent = TriggerZoneChange.instance(This.instance());
			SetGenerator enteringPermanent = NewObjectOf.instance(triggerEvent);
			SetGenerator theOtherCreature = Intersect.instance(anotherCreature, enteringPermanent);

			EventFactory counterFactory = new EventFactory(EventType.MOVE_COUNTERS, "Move a +1/+1 counter from this permanent onto that creature");
			counterFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			counterFactory.parameters.put(EventType.Parameter.FROM, ABILITY_SOURCE_OF_THIS);
			counterFactory.parameters.put(EventType.Parameter.TO, theOtherCreature);
			counterFactory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));

			this.addEffect(youMay(counterFactory, "You may move a +1/+1 counter from this permanent onto that creature."));
		}
	}
}
