package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Poisoned extends SetGenerator
{
	private static Poisoned _instance = null;

	public static Poisoned instance()
	{
		if(_instance == null)
			_instance = new Poisoned();
		return _instance;
	}

	private Poisoned()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(Player player: state.players)
			if(player.countPoisonCounters() > 0)
				ret.add(player);

		return ret;
	}

}
