package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ModesOf extends SetGenerator
{
	public static ModesOf instance(SetGenerator what)
	{
		return new ModesOf(what);
	}

	private SetGenerator what;

	private ModesOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			for(java.util.List<Mode> modes: object.getModes())
				ret.addAll(modes);

		return ret;
	}

}
