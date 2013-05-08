package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Multicolored extends SetGenerator
{
	private static final Multicolored _instance = new Multicolored();

	public static Multicolored instance()
	{
		return _instance;
	}

	private Multicolored()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.getAllObjects())
			if(object.getColors().size() > 1)
				ret.add(object);

		return ret;
	}
}
