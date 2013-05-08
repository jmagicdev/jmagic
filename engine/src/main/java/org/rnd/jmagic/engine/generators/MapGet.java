package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class MapGet extends SetGenerator
{
	public static MapGet instance(SetGenerator key, java.util.Map<?, ?> map)
	{
		return new MapGet(key, map);
	}

	private SetGenerator key;
	private java.util.Map<?, ?> map;

	private MapGet(SetGenerator key, java.util.Map<?, ?> map)
	{
		this.key = key;
		this.map = map;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Object object: this.key.evaluate(state, thisObject))
			if(this.map.containsKey(object))
				ret.add(this.map.get(object));
		return ret;
	}

}
