package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Metalcraft
{
	private static SetGenerator _instance = null;

	public static SetGenerator instance()
	{
		if(_instance == null)
			_instance = Intersect.instance(Between.instance(3, null), Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), ArtifactPermanents.instance())));
		return _instance;
	}
}
