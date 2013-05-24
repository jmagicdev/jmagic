package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the rfg zone
 */
public class ExileZone extends SetGenerator
{
	private static final ExileZone _instance = new ExileZone();

	public static ExileZone instance()
	{
		return _instance;
	}

	private ExileZone()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return new Set(state.exileZone());
	}
}
