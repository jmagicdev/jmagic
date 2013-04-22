package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players library
 */
public class LibraryOf extends SetGenerator
{
	public static LibraryOf instance(SetGenerator what)
	{
		return new LibraryOf(what);
	}

	private final SetGenerator players;

	private LibraryOf(SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Player p: this.players.evaluate(state, thisObject).getAll(Player.class))
			ret.add(p.getLibrary(state));
		return ret;
	}

}
