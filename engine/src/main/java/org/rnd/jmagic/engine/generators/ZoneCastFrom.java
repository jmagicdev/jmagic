package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ZoneCastFrom extends SetGenerator
{
	public static ZoneCastFrom instance(SetGenerator what)
	{
		return new ZoneCastFrom(what);
	}

	private final SetGenerator what;

	private ZoneCastFrom(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			if(-1 != o.zoneCastFrom)
				ret.add(state.<Zone>get(o.zoneCastFrom));
		return ret;
	}
}
