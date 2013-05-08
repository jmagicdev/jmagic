package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Suspended extends SetGenerator
{
	private static SetGenerator _instance = null;

	public static SetGenerator instance()
	{
		if(_instance == null)
			_instance = Intersect.instance(InZone.instance(ExileZone.instance()), Intersect.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Suspend.class), HasCounterOfType.instance(Counter.CounterType.TIME)));
		return _instance;
	}

	private Suspended()
	{
		// Private constructor for singleton design
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.exileZone().objects)
			if(object.isSuspended())
				ret.add(object);

		return ret;
	}
}
