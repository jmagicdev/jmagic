package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the current phase
 */
public class CurrentPhase extends SetGenerator
{
	private static final CurrentPhase _instance = new CurrentPhase();

	public static CurrentPhase instance()
	{
		return _instance;
	}

	private CurrentPhase()
	{
		// Intentionally left ineffectual
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.currentPhase() != null)
			return new Set(state.currentPhase());
		return Empty.set;
	}
}
