package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public final class TargetsAreLegal extends SetGenerator
{
	private SetGenerator targets;

	private TargetsAreLegal(SetGenerator targets)
	{
		this.targets = targets;
	}

	public static SetGenerator instance(SetGenerator targets)
	{
		return new TargetsAreLegal(targets);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		for(Target t: this.targets.evaluate(state, thisObject).getAll(Target.class))
			if(!t.isLegal(state.game, (GameObject)thisObject))
				return Empty.set;
		return NonEmpty.set;
	}
}