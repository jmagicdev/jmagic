package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the command zone
 */
public class CommandZone extends SetGenerator
{
	private static final CommandZone _instance = new CommandZone();

	public static CommandZone instance()
	{
		return _instance;
	}

	private CommandZone()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return new Set(state.commandZone());
	}
}
