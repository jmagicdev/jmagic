package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the types of each of the given events
 */
public class EventTypeOf extends SetGenerator
{
	public static EventTypeOf instance(SetGenerator what)
	{
		return new EventTypeOf(what);
	}

	private final SetGenerator events;

	private EventTypeOf(SetGenerator events)
	{
		this.events = events;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Event event: this.events.evaluate(state, thisObject).getAll(Event.class))
			ret.add(event.type);
		return ret;
	}
}
