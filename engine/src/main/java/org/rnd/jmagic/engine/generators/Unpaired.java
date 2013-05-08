package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Unpaired extends SetGenerator
{
	private static Unpaired instance = null;

	public static SetGenerator instance()
	{
		if(null == instance)
			instance = new Unpaired();
		return instance;
	}

	private Unpaired()
	{
		// Do nothing
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: state.getAllObjects())
			if(null == o.getPairedWith(state))
				ret.add(o);
		return ret;
	}
}
