package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class PairedWith extends SetGenerator
{
	public static SetGenerator instance(SetGenerator what)
	{
		return new PairedWith(what);
	}

	private SetGenerator what;

	private PairedWith(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
		{
			GameObject pair = o.getPairedWith(state);
			if(null != pair)
				ret.add(pair);
		}
		return ret;
	}
}
