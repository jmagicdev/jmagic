package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to a specific parameter of each of the given events
 */
public class EventParameter extends SetGenerator
{
	public static EventParameter instance(SetGenerator events, EventType.Parameter type, boolean evaluate)
	{
		return new EventParameter(events, type, evaluate);
	}

	public static EventParameter instance(SetGenerator events, EventType.Parameter type)
	{
		return new EventParameter(events, type, true);
	}

	private final SetGenerator events;
	private EventType.Parameter type;
	private boolean evaluate;

	private EventParameter(SetGenerator events, EventType.Parameter type, boolean evaluate)
	{
		this.events = events;
		this.type = type;
		this.evaluate = evaluate;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Event event: this.events.evaluate(state, thisObject).getAll(Event.class))
			if(event.parametersNow != null && event.parametersNow.containsKey(this.type))
			{
				Identity mostRecent = event.parametersNow.get(this.type);
				ret.add(mostRecent);
			}
			// if the event is in the process of being replaced, its parameters
			// are intact.
			else if(event.parameters.containsKey(this.type))
				ret.add(event.parameters.get(this.type));

		// If we're supposed to be evaluating these generators, do it.
		// Otherwise, return them.
		if(this.evaluate)
		{
			Set newRet = new Set();

			for(SetGenerator generator: ret.getAll(SetGenerator.class))
				newRet.addAll(generator.evaluate(state, thisObject));
			return newRet;
		}

		return ret;
	}
}
