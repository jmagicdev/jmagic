package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.40. Modular
 * 
 * 702.40a Modular represents both a static ability and a triggered ability.
 * "Modular N" means "This permanent enters the battlefield with N +1/+1
 * counters on it" and "When this permanent is put into a graveyard from the
 * battlefield, you may put a +1/+1 counter on target artifact creature for each
 * +1/+1 counter on this permanent."
 * 
 * 702.40b If a creature has multiple instances of modular, each one works
 * separately.
 */
public final class Modular extends Keyword
{
	// This is a setgenerator in preparation for "Modular\u2014Sunburst".
	private final SetGenerator N;
	private final String counterString;

	public Modular(GameState state, SetGenerator N, String counterString, String name)
	{
		super(state, name);
		this.N = N;
		this.counterString = counterString;
	}

	public Modular(GameState state, int N)
	{
		this(state, numberGenerator(N), N + " +1/+1 counters on it", "Modular " + N);
	}

	@Override
	public Modular create(Game game)
	{
		return new Modular(game.physicalState, this.N, this.counterString, this.getName());
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(this.state, "This permanent", this.N, this.counterString, Counter.CounterType.PLUS_ONE_PLUS_ONE));
	}

	public static final class ModularTransfer extends EventTriggeredAbility
	{
		public ModularTransfer(GameState state)
		{
			super(state, "When this dies, you may put its +1/+1 counters on target artifact creature.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			Target target = this.addTarget(Intersect.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact creature");

			SetGenerator number = Count.instance(CountersOn.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance()))));

			EventFactory putCounters = new EventFactory(EventType.PUT_COUNTERS, "Put its +1/+1 counters on target artifact creature.");
			putCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			putCounters.parameters.put(EventType.Parameter.NUMBER, number);
			putCounters.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(youMay(putCounters, "You may put its +1/+1 counters on target artifact creature."));
		}
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new ModularTransfer(this.state));
	}

}
