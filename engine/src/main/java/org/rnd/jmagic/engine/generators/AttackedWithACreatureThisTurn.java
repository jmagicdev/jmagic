package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.trackers.*;

/**
 * Requires AttackTracker.
 */
public final class AttackedWithACreatureThisTurn extends SetGenerator
{
	private static final AttackedWithACreatureThisTurn _instance = new AttackedWithACreatureThisTurn();

	public static AttackedWithACreatureThisTurn instance()
	{
		return _instance;
	}

	private AttackedWithACreatureThisTurn()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		AttackTracker tracker = state.getTracker(AttackTracker.class);
		Set ret = new Set();
		for(int playerID: tracker.getValue(state))
			ret.add(state.get(playerID));
		return ret;
	}
}