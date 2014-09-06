package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class CountChosenTargets extends SetGenerator
{
	public static CountChosenTargets instance(SetGenerator what, SetGenerator targets)
	{
		return new CountChosenTargets(what, targets);
	}

	private SetGenerator what;
	private SetGenerator targets;

	private CountChosenTargets(SetGenerator what, SetGenerator targets)
	{
		this.what = what;
		this.targets = targets;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		int i = 0;
		java.util.Set<Target> targets = this.targets.evaluate(state, thisObject).getAll(Target.class);
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
		{
			java.util.Map<Target, java.util.List<Target>> chosenTargets = new java.util.HashMap<>();
			for(java.util.Map<Target, java.util.List<Target>> characteristicChosenTargets: o.getChosenTargets())
				chosenTargets.putAll(characteristicChosenTargets);

			for(Target target: targets)
				if(chosenTargets.containsKey(target))
					i += chosenTargets.get(target).size();
		}
		return new Set(i);
	}

}
