package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.trackers.*;

/**
 * Evaluates to all players that have drawn at least one card this turn.
 * 
 * Requires {@link DrawTracker}.
 */
public final class DrawnACardThisTurn extends SetGenerator
{
	private static SetGenerator _instance = new DrawnACardThisTurn();

	public static SetGenerator instance()
	{
		return _instance;
	}

	private DrawnACardThisTurn()
	{
		// singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return IdentifiedWithID.instance(state.getTracker(DrawTracker.class).getValue(state).keySet())//
		.evaluate(state, thisObject);
	}
}