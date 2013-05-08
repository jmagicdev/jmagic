package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Revealed extends SetGenerator
{
	private static Revealed _instance = null;

	public static Revealed instance()
	{
		if(_instance == null)
			_instance = new Revealed();
		return _instance;
	}

	private Revealed()
	{
		// intentionally blank
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		int numPlayers = state.players.size();

		for(GameObject object: state.getAllObjects())
			if(object.getVisibleTo().size() == numPlayers)
				ret.add(object);

		return ret;
	}
}
