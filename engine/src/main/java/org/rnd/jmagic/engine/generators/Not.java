package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to an empty set given a non-empty set, or a non-empty set given an
 * empty set
 */
public class Not extends SetGenerator
{
	public static Not instance(SetGenerator what)
	{
		return new Not(what);
	}

	private final SetGenerator what;

	private Not(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(this.what.evaluate(state, thisObject).isEmpty())
			return NonEmpty.set;
		return Empty.set;
	}
}
