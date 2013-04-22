package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Returns the largest Set out of the Sets given. If none are given, returns the
 * empty set. If two or more sets are tied for largest, returns an arbitary one.
 */
public class LargestSet extends SetGenerator
{
	public static LargestSet instance(SetGenerator what)
	{
		return new LargestSet(what);
	}

	private SetGenerator what;

	private LargestSet(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Set set: this.what.evaluate(state, thisObject).getAll(Set.class))
			if(set.size() > ret.size())
				ret = set;
		return ret;
	}
}
