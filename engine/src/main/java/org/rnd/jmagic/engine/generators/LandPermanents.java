package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class LandPermanents extends SetGenerator
{
	private static final LandPermanents _instance = new LandPermanents();

	public static LandPermanents instance()
	{
		return _instance;
	}

	private LandPermanents()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.battlefield().objects)
			if(object.getTypes().contains(Type.LAND))
				ret.add(object);

		return ret;
	}
}
