package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class DetainGenerator
{
	public static final class Tracker extends UntilNextTurn.EventAndBeginTurnTracker
	{
		public Tracker()
		{
			super(EventType.DETAIN);
		}
	}

	private static SetGenerator _instance = UntilNextTurn.instance(Tracker.class);

	public static SetGenerator instance()
	{
		return _instance;
	}
}
