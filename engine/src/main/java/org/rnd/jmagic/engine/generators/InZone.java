package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the objects in each of the given zones
 */
public class InZone extends SetGenerator
{
	public static InZone instance(SetGenerator what)
	{
		return new InZone(what);
	}

	private final SetGenerator zones;

	private InZone(SetGenerator zones)
	{
		this.zones = zones;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Zone z: this.zones.evaluate(state, thisObject).getAll(Zone.class))
			ret.addAll(z.objects);
		return ret;
	}
}
