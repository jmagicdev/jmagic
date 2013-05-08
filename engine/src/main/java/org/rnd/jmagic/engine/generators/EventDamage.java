package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all the damage the given event(s) dealt
 */
public class EventDamage extends SetGenerator
{
	public static EventDamage instance(SetGenerator what)
	{
		return new EventDamage(what);
	}

	private SetGenerator what;

	private EventDamage(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Event event: this.what.evaluate(state, thisObject).getAll(Event.class))
			ret.addAll(event.getDamage());
		return ret;
	}
}
