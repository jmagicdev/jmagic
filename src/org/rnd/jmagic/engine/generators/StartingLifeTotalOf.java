package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class StartingLifeTotalOf extends SetGenerator
{
	public static StartingLifeTotalOf instance(SetGenerator who)
	{
		return new StartingLifeTotalOf(who);
	}

	@SuppressWarnings("unused")
	private SetGenerator who;

	private StartingLifeTotalOf(SetGenerator who)
	{
		this.who = who;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		// TODO : MAKE THIS CORRECT!
		return new Set(20);
	}
}
