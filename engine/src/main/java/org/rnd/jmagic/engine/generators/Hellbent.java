package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Hellbent
{
	private static final SetGenerator _instance = Not.instance(InZone.instance(HandOf.instance(You.instance())));

	public static SetGenerator instance()
	{
		return _instance;
	}

	private Hellbent()
	{
		// Singleton Constructor
	}
}
