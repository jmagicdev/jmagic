package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the current turn
 */
public class CurrentTurn extends SetGenerator
{
	private static final CurrentTurn _instance = new CurrentTurn();

	public static CurrentTurn instance()
	{
		return _instance;
	}

	private CurrentTurn()
	{
		// Intentionally left ineffectual
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.currentTurn() != null)
			return new Set(state.currentTurn());
		return Empty.set;
	}
}
