package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the opponents of a single given player
 */
public class OpponentsOf extends SetGenerator
{
	public static OpponentsOf instance(SetGenerator what)
	{
		return new OpponentsOf(what);
	}

	private final SetGenerator players;

	public static Set get(GameState state, Player subject)
	{
		// we don't support teams yet, so...
		Set ret = new Set();
		if(subject == null)
			return ret;
		for(Player player: Players.get(state).getAll(Player.class))
		{
			// ... for now, you have no friends.
			if(player.ID != subject.ID)
				ret.add(player);
		}
		return ret;
	}

	private OpponentsOf(SetGenerator players)
	{
		this.players = players;
	}

	// This will only return the opponents of a single player in the set
	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return OpponentsOf.get(state, this.players.evaluate(state, thisObject).getOne(Player.class));
	}
}
