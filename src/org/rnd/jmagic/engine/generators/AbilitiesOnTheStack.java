package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public final class AbilitiesOnTheStack extends SetGenerator
{
	private static SetGenerator _instance;

	public static SetGenerator instance()
	{
		if(_instance == null)
			_instance = new AbilitiesOnTheStack();
		return _instance;
	}

	private AbilitiesOnTheStack()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject onStack: state.stack().objects)
			if(onStack.isActivatedAbility() || onStack.isTriggeredAbility())
				ret.add(onStack);
		return ret;
	}
}