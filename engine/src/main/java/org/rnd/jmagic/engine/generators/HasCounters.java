package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class HasCounters extends SetGenerator
{
	private static HasCounters _instance = null;

	public static HasCounters instance()
	{
		if(_instance == null)
			_instance = new HasCounters();
		return _instance;
	}

	private HasCounters()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(Identified i: state.getAll(Identified.class))
		{
			if(i instanceof GameObject)
			{
				if(!((GameObject)i).counters.isEmpty())
					ret.add(i);
			}
			else if(i instanceof Player)
			{
				if(!((Player)i).counters.isEmpty())
					ret.add(i);
			}
		}

		return ret;
	}

}
