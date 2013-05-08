package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the player with priority
 */
public class PlayerWithPriority extends org.rnd.jmagic.engine.SetGenerator
{
	private static final PlayerWithPriority _instance = new PlayerWithPriority();

	public static PlayerWithPriority instance()
	{
		return _instance;
	}

	private PlayerWithPriority()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Player playerWithPriority = state.getPlayerWithPriority();
		if(playerWithPriority == null)
			return Empty.set;

		return new Set(playerWithPriority);
	}
}
