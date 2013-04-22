package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players graveyard
 */
public class GraveyardOf extends SetGenerator
{
	public static GraveyardOf instance(SetGenerator what)
	{
		return new GraveyardOf(what);
	}

	private final SetGenerator players;

	private GraveyardOf(SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Player p: this.players.evaluate(state, thisObject).getAll(Player.class))
			ret.add(p.getGraveyard(state));
		return ret;
	}

}
