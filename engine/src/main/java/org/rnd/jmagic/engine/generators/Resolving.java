package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If an object is currently resolving, evaluates to that object. Otherwise
 * empty.
 */
public class Resolving extends SetGenerator
{
	private static final Resolving _instance = new Resolving();

	public static Resolving instance()
	{
		return _instance;
	}

	private Resolving()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.resolvingID == -1)
			return Empty.set;
		return new Set(state.get(state.resolvingID));
	}
}
