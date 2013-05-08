package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ArtifactPermanents extends SetGenerator
{
	private static final SetGenerator _instance = Intersect.instance(Permanents.instance(), HasType.instance(Type.ARTIFACT));

	public static SetGenerator instance()
	{
		return _instance;
	}

	private ArtifactPermanents()
	{
		// Private Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.battlefield().objects)
			if(object.getTypes().contains(Type.ARTIFACT))
				ret.add(object);

		return ret;
	}
}
