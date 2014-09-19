package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.trackers.*;

/**
 * Requires AttackTracker.
 */
public final class Raid extends SetGenerator
{
	private static final Raid _instance = new Raid();

	public static Raid instance()
	{
		return _instance;
	}

	private Raid()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		int you = You.instance().evaluate(state, thisObject).getOne(Player.class).ID;

		return state.getTracker(AttackTracker.class).getValue(state).stream()//
		.anyMatch(id -> id == you)//
		? NonEmpty.set : Empty.set;
	}
}