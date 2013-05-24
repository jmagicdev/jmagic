package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject which has the given SuperType
 */
public class HasSuperType extends SetGenerator
{
	private static final java.util.Map<SuperType, HasSuperType> _instances = new java.util.HashMap<SuperType, HasSuperType>();

	public static HasSuperType instance(SuperType what)
	{
		if(!_instances.containsKey(what))
			_instances.put(what, new HasSuperType(Identity.instance(what)));
		return _instances.get(what);
	}

	public static HasSuperType instance(SetGenerator what)
	{
		return new HasSuperType(what);
	}

	private final SetGenerator type;

	private HasSuperType(SetGenerator type)
	{
		this.type = type;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Set<SuperType> types = this.type.evaluate(state, thisObject).getAll(SuperType.class);
		for(GameObject item: state.getAllObjects())
			for(SuperType type: types)
				if(item.getSuperTypes().contains(type))
				{
					ret.add(item);
					break;
				}
		return ret;
	}
}
