package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject on that stack or in the given set which is a
 * spell
 */
public class Spells extends SetGenerator
{
	private static final Spells _instance = new Spells();

	public static Spells instance()
	{
		return _instance;
	}

	private Spells()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject object: state.stack())
			if(object.isSpell())
				ret.add(object);
		return ret;
	}
}
