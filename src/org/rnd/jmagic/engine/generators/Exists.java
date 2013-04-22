package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all GameObjects in the given set that aren't ghosts
 */
public class Exists extends SetGenerator
{
	public static SetGenerator instance(SetGenerator what)
	{
		return new Exists(what);
	}

	private Exists(SetGenerator what)
	{
		this.what = what;
	}

	private SetGenerator what;

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			if(!o.isGhost())
				ret.add(o);
		return ret;
	}

}
