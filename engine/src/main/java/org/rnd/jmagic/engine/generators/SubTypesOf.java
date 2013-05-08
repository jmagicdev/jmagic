package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Returns all the SubTypes of the given objects that are associated with the
 * given type. For instance, only creature types will be returned if type is
 * either Creature or Tribal.
 */
public class SubTypesOf extends SetGenerator
{
	public static SubTypesOf instance(SetGenerator what, Type type)
	{
		return new SubTypesOf(what, type);
	}

	private SetGenerator what;
	private Type type;

	private SubTypesOf(SetGenerator what, Type type)
	{
		this.what = what;
		this.type = type;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			ret.addAll(SubType.getSubTypesFor(this.type, object.getSubTypes()));

		return ret;
	}
}
