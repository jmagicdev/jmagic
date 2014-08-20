package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class MapKeySet extends SetGenerator
{
	public static MapKeySet instance(java.util.Map<?, ?> map)
	{
		return new MapKeySet(map);
	}

	private java.util.Map<?, ?> map;

	private MapKeySet(java.util.Map<?, ?> map)
	{
		this.map = map;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return Set.fromCollection(this.map.keySet());
	}
}
