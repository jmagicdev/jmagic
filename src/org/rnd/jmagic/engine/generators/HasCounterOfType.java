package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject which has (a | at least the given number of)
 * counter(s) of the given type on it
 */
public class HasCounterOfType extends SetGenerator
{
	public static HasCounterOfType instance(Counter.CounterType what)
	{
		return new HasCounterOfType(1, Identity.instance(what));
	}

	public static HasCounterOfType instance(int number, Counter.CounterType what)
	{
		return new HasCounterOfType(number, Identity.instance(what));
	}

	public static HasCounterOfType instance(SetGenerator what)
	{
		return new HasCounterOfType(1, what);
	}

	private final int number;
	private final SetGenerator counter;

	private HasCounterOfType(int number, SetGenerator counter)
	{
		this.number = number;
		this.counter = counter;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Set<Counter.CounterType> types = new java.util.HashSet<Counter.CounterType>();
		Set counters = this.counter.evaluate(state, thisObject);
		for(Counter counter: counters.getAll(Counter.class))
			types.add(counter.getType());
		for(Counter.CounterType type: counters.getAll(Counter.CounterType.class))
			types.add(type);
		for(GameObject object: state.getAllObjects())
		{
			int count = 0;
			for(Counter counter: object.counters)
				if(types.contains(counter.getType()))
				{
					count++;
					if(count >= this.number)
					{
						ret.add(object);
						break;
					}
				}
		}

		return ret;
	}

}
