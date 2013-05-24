package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Returns a set containing all nontoken permanents.
 */
public class NonToken extends SetGenerator
{
	private static final NonToken _instance = new NonToken();

	public static NonToken instance()
	{
		return _instance;
	}

	private NonToken()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.battlefield().objects)
			if(!object.isToken())
				ret.add(object);

		return ret;
	}

}
