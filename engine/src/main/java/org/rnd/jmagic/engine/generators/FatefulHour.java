package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If you have 5 or less life...
 */
public class FatefulHour extends SetGenerator
{
	private static FatefulHour _instance = null;

	public static FatefulHour instance()
	{
		if(_instance == null)
			_instance = new FatefulHour();
		return _instance;
	}

	private FatefulHour()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		for(Player player: You.instance().evaluate(state, thisObject).getAll(Player.class))
			if(player.lifeTotal <= 5)
				return NonEmpty.set;
		return Empty.set;
	}

}
