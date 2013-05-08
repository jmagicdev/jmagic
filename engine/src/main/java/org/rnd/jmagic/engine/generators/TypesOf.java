package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class TypesOf extends SetGenerator
{
	public static TypesOf instance(SetGenerator what)
	{
		return new TypesOf(what);
	}

	private SetGenerator what;

	private TypesOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			ret.addAll(o.getTypes());
		return ret;
	}

}
