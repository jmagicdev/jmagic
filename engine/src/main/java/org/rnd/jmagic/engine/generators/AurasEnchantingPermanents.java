package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class AurasEnchantingPermanents extends SetGenerator
{
	private static AurasEnchantingPermanents _instance = null;

	public static AurasEnchantingPermanents instance()
	{
		if(_instance == null)
			_instance = new AurasEnchantingPermanents();
		return _instance;
	}

	private AurasEnchantingPermanents()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: state.battlefield().objects)
		{
			if(o.getTypes().contains(Type.ENCHANTMENT) && o.getSubTypes().contains(SubType.AURA) && o.getAttachedTo() != -1)
			{
				Identified bearer = state.get(o.getAttachedTo());
				if(bearer.isPermanent())
					ret.add(o);
			}
		}
		return ret;
	}
}
