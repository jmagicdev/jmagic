package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Threshold extends SetGenerator
{
	private static final Threshold _instance = new Threshold();

	public static Threshold instance()
	{
		return _instance;
	}

	private Threshold()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		for(Player player: You.instance().evaluate(state, thisObject).getAll(Player.class))
			if(player.getGraveyard(state).objects.size() >= 7)
				return NonEmpty.set;
		return Empty.set;
	}

}
