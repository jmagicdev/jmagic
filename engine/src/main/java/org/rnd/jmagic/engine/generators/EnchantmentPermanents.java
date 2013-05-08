package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class EnchantmentPermanents extends SetGenerator
{
	private static final SetGenerator _instance = new EnchantmentPermanents();

	public static SetGenerator instance()
	{
		return _instance;
	}

	private EnchantmentPermanents()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.battlefield().objects)
			if(object.getTypes().contains(Type.ENCHANTMENT))
				ret.add(object);

		return ret;
	}
}
