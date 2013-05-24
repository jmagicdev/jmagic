package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each {@link GameObject} which does not have the any of the given
 * {@link Type}s
 */
public class DoesntHaveType extends SetGenerator
{
	private static final java.util.Map<Type, DoesntHaveType> _instances = new java.util.HashMap<Type, DoesntHaveType>();

	public static Set get(GameState state, java.util.Collection<Type> types)
	{
		Set ret = new Set();
		items: for(GameObject item: state.getAllObjects())
		{
			for(Type type: types)
				if(item.getTypes().contains(type))
					continue items;
			ret.add(item);
		}
		return ret;
	}

	public static DoesntHaveType instance(Type what)
	{
		if(!_instances.containsKey(what))
			_instances.put(what, new DoesntHaveType(Identity.instance(what)));
		return _instances.get(what);
	}

	public static DoesntHaveType instance(Type... what)
	{
		return new DoesntHaveType(Identity.instance((Object[])what));
	}

	public static SetGenerator instance(java.util.Collection<Type> what)
	{
		return new DoesntHaveType(Identity.instance(what));
	}

	public static DoesntHaveType instance(SetGenerator what)
	{
		return new DoesntHaveType(what);
	}

	private final SetGenerator type;

	private DoesntHaveType(SetGenerator type)
	{
		this.type = type;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return get(state, this.type.evaluate(state, thisObject).getAll(Type.class));
	}
}
