package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RemoveAllCounters extends EventType
{	public static final EventType INSTANCE = new RemoveAllCounters();

	 private RemoveAllCounters()
	{
		super("REMOVE_ALL_COUNTERS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();
		Set types = parameters.get(Parameter.COUNTER);

		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			java.util.Collection<Event> removeEvents = new java.util.LinkedList<Event>();
			for(Counter counter: object.counters)
			{
				if(types == null || types.contains(counter.getType()))
				{
					java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
					counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					counterParameters.put(Parameter.COUNTER, new Set(counter.getType()));
					counterParameters.put(Parameter.OBJECT, new Set(object));
					Event removeCounter = createEvent(game, "Remove a " + counter.getType() + " from " + object + ".", EventType.REMOVE_ONE_COUNTER, counterParameters);
					removeEvents.add(removeCounter);
				}
			}

			for(Event e: removeEvents)
			{
				e.perform(event, false);
				result.addAll(e.getResult());
			}
		}

		event.setResult(Identity.fromCollection(result));
		return true;
	}
}