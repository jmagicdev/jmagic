package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the current game
 */
public class CurrentGame extends SetGenerator
{
	private static final CurrentGame _instance = new CurrentGame();

	public static CurrentGame instance()
	{
		return _instance;
	}

	private CurrentGame()
	{
		// Intentionally left ineffectual
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return new Set(state.game);
	}
}
