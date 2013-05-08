package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject which has the any of the given Types
 */
public class HasType extends SetGenerator
{
	private static final java.util.Map<Type, HasType> _instances = new java.util.HashMap<Type, HasType>();

	public static Set get(GameState state, java.util.Collection<Type> types)
	{
		Set ret = new Set();
		for(GameObject item: state.getAllObjects())
			for(Type type: types)
				if(item.getTypes().contains(type))
				{
					ret.add(item);
					break;
				}
		return ret;
	}

	public static HasType instance(Type what)
	{
		if(!_instances.containsKey(what))
			_instances.put(what, new HasType(Identity.instance(what)));
		return _instances.get(what);
	}

	public static HasType instance(Type... what)
	{
		return new HasType(Identity.instance((Object[])what));
	}

	public static SetGenerator instance(java.util.Collection<Type> what)
	{
		return new HasType(Identity.instance(what));
	}

	public static HasType instance(SetGenerator what)
	{
		return new HasType(what);
	}

	private final SetGenerator type;

	private HasType(SetGenerator type)
	{
		this.type = type;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return get(state, this.type.evaluate(state, thisObject).getAll(Type.class));
	}
}
