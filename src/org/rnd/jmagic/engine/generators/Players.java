package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all players
 */
public class Players extends org.rnd.jmagic.engine.SetGenerator
{
	private static final Players _instance = new Players();

	public static Players instance()
	{
		return _instance;
	}

	public static Set get(GameState state)
	{
		Set ret = new Set();
		for(Player p: state.players)
			if(!p.outOfGame)
				ret.add(p);
		return ret;
	}

	private Players()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return Players.get(state);
	}
}
