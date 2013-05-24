package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the targeted object of each given Target
 */
public class ExtractTargets extends SetGenerator
{
	public static ExtractTargets instance(SetGenerator what)
	{
		return new ExtractTargets(what);
	}

	private final SetGenerator targets;

	private ExtractTargets(SetGenerator targets)
	{
		this.targets = targets;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Target target: this.targets.evaluate(state, thisObject).getAll(Target.class))
			if(state.containsIdentified(target.targetID))
				ret.add(state.get(target.targetID));
		return ret;
	}
}
