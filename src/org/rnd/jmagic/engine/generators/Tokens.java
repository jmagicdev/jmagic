package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Tokens extends SetGenerator
{
	private static final Tokens _instance = new Tokens();

	public static Tokens instance()
	{
		return _instance;
	}

	private Tokens()
	{
		// Singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject o: state.getAll(Token.class))
			if(!o.isGhost())
				ret.add(o);

		return ret;
	}
}
