package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players hand
 */
public class HandOf extends SetGenerator
{
	public static HandOf instance(SetGenerator what)
	{
		return new HandOf(what);
	}

	private final SetGenerator players;

	private HandOf(org.rnd.jmagic.engine.SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Player p: this.players.evaluate(state, thisObject).getAll(Player.class))
			ret.add(p.getHand(state));
		return ret;
	}
}
