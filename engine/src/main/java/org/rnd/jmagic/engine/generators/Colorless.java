package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Colorless extends SetGenerator
{
	private static final Colorless _instance = new Colorless();

	public static Colorless instance()
	{
		return _instance;
	}

	private Colorless()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.getAllObjects())
			if(object.getColors().isEmpty())
				ret.add(object);

		return ret;
	}
}
