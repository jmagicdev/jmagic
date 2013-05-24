package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class MoveCounters extends EventType
{	public static final EventType INSTANCE = new MoveCounters();

	 private MoveCounters()
	{
		super("MOVE_COUNTERS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.COUNTER;
	}

	// there was a commented-out attempt() method here. if you want it, go
	// back through the repository and find it.

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject from = parameters.get(Parameter.FROM).getOne(GameObject.class);
		GameObject to = parameters.get(Parameter.TO).getOne(GameObject.class);

		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));

		Counter.CounterType type = null;
		if(parameters.containsKey(Parameter.COUNTER))
			type = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);

		boolean success = true;
		Set result = new Set();

		if(number < 1)
		{
			event.setResult(Identity.instance(result));
			return true;
		}

		java.util.Map<Parameter, Set> removeParameters = new java.util.HashMap<Parameter, Set>();
		removeParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		removeParameters.put(Parameter.COUNTER, new Set(type));
		removeParameters.put(Parameter.NUMBER, new Set(number));
		removeParameters.put(Parameter.OBJECT, new Set(from));
		Event removeEvent = createEvent(game, "Remove counters from " + from.getName(), EventType.REMOVE_COUNTERS, removeParameters);
		success = removeEvent.perform(event, false) && success;

		java.util.Map<Parameter, Set> addParameters = new java.util.HashMap<Parameter, Set>();
		addParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		addParameters.put(Parameter.COUNTER, new Set(type));
		addParameters.put(Parameter.NUMBER, new Set(removeEvent.getResult().size()));
		addParameters.put(Parameter.OBJECT, new Set(to));
		Event addEvent = createEvent(game, "Put counters on " + to.getName(), EventType.PUT_COUNTERS, addParameters);
		success = addEvent.perform(event, false) && success;

		result.addAll(removeEvent.getResult());
		result.addAll(addEvent.getResult());

		event.setResult(Identity.instance(result));

		return success;
	}
}