package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class NameOf extends SetGenerator
{
	public static NameOf instance(SetGenerator what)
	{
		return new NameOf(what);
	}

	private SetGenerator what;

	private NameOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Identified i: this.what.evaluate(state, thisObject).getAll(Identified.class))
			ret.add(i.getName());
		return ret;
	}

}
