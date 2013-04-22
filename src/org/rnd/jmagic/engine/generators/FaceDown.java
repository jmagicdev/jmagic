package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Returns all objects with {@link GameObject#faceDownValues} not null (that is,
 * all face down permanents).
 */
public class FaceDown extends SetGenerator
{
	private final static FaceDown _instance = new FaceDown();

	public static FaceDown instance()
	{
		return _instance;
	}

	private FaceDown()
	{
		// Private Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: state.getAllObjects())
			if(null != o.faceDownValues)
				ret.add(o);
		return ret;
	}

}
