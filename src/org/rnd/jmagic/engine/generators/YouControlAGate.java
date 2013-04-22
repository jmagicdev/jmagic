package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class YouControlAGate
{
	private static SetGenerator _instance = null;

	public static SetGenerator instance()
	{
		if(_instance == null)
			_instance = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.GATE));
		return _instance;
	}
}
