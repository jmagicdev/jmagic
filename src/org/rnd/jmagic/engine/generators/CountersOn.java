package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the counters placed on each of the given GameObjects.
 * Optionally, it can get counters only of a specific type.
 */
public class CountersOn extends SetGenerator
{
	public static CountersOn instance(SetGenerator what)
	{
		return new CountersOn(what, null);
	}

	public static CountersOn instance(SetGenerator what, Counter.CounterType type)
	{
		return new CountersOn(what, type);
	}

	private final SetGenerator what;
	private Counter.CounterType type;

	public static Set get(GameObject what, Counter.CounterType type)
	{
		Set ret = new Set();
		for(Counter counter: what.counters)
			if(counter.getType() == type)
				ret.add(counter);
		return ret;
	}

	public static Set get(Player what, Counter.CounterType type)
	{
		Set ret = new Set();
		for(Counter counter: what.counters)
			if(counter.getType() == type)
				ret.add(counter);
		return ret;
	}

	private CountersOn(SetGenerator what, Counter.CounterType type)
	{
		this.what = what;
		this.type = type;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		Set objects = this.what.evaluate(state, thisObject);
		for(GameObject object: objects.getAll(GameObject.class))
		{
			if(this.type == null)
				ret.addAll(object.counters);
			else
				ret.addAll(CountersOn.get(object, this.type));
		}
		for(Player player: objects.getAll(Player.class))
		{
			if(this.type == null)
				ret.addAll(player.counters);
			else
				ret.addAll(CountersOn.get(player, this.type));
		}
		return ret;
	}

}
