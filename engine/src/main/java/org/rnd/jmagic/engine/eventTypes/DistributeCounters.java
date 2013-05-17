package org.rnd.jmagic.engine.eventTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DistributeCounters extends EventType
{	public static final EventType INSTANCE = new DistributeCounters();

	 private DistributeCounters()
	{
		super("DISTRIBUTE_COUNTERS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject source = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		Counter.CounterType counter = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
		Set receivers = parameters.get(Parameter.OBJECT);

		EventFactory[] putCounters = new EventFactory[receivers.size()];
		int i = 0;
		for(Target receiver: receivers.getAll(Target.class))
			putCounters[i++] = putCounters(receiver.division, counter, IdentifiedWithID.instance(receiver.targetID), "");

		simultaneous(putCounters).createEvent(game, source).perform(event, false);
		event.setResult(Empty.set);
		return true;
	}
}