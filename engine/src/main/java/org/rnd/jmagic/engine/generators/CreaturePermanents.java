package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class CreaturePermanents extends SetGenerator
{
	private static final CreaturePermanents _instance = new CreaturePermanents();

	public static SetGenerator instance()
	{
		return _instance;
	}

	private CreaturePermanents()
	{
		// Private Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.battlefield().objects)
			if(object.getTypes().contains(Type.CREATURE))
				ret.add(object);

		return ret;
	}

}
