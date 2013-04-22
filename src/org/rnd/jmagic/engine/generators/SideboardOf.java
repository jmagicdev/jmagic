package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players graveyard
 */
public class SideboardOf extends SetGenerator
{
	public static SideboardOf instance(SetGenerator what)
	{
		return new SideboardOf(what);
	}

	private final SetGenerator players;

	private SideboardOf(SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Player p: this.players.evaluate(state, thisObject).getAll(Player.class))
			ret.add(p.getSideboard(state));
		return ret;
	}

}
