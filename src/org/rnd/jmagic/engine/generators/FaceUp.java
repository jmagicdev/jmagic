package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Returns all objects that are visible to all players outside the battlefield,
 * and all objects with faceDown set to null within the battlefield.
 */
public class FaceUp extends SetGenerator
{
	private final static FaceUp _instance = new FaceUp();

	public static FaceUp instance()
	{
		return _instance;
	}

	private FaceUp()
	{
		// Private Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		int numPlayers = state.players.size();
		for(GameObject o: state.getAllObjects())
		{
			if(o.isPermanent())
			{
				if(null == o.faceDownValues)
					ret.add(o);
			}
			else
			{
				Zone z = o.getZone();
				if(z != null && o.getPhysical().getVisibleTo().size() == numPlayers)
					ret.add(o);
			}
		}
		return ret;
	}

}
