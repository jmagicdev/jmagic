package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class HasOneTarget extends SetGenerator
{
	private static final HasOneTarget _instance = new HasOneTarget();

	public static HasOneTarget instance()
	{
		return _instance;
	}

	private HasOneTarget()
	{
		// Singleton Constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: state.stack().objects)
		{
			java.util.Map<Target, java.util.List<Target>> chosenTargets = new java.util.HashMap<>();
			for(java.util.Map<Target, java.util.List<Target>> characteristicChosenTargets: object.getChosenTargets())
				chosenTargets.putAll(characteristicChosenTargets);

			int targetCount = 0;
			for(Mode mode: object.getSelectedModes())
				for(Target possibleTarget: mode.targets)
					if(chosenTargets.containsKey(possibleTarget))
						targetCount += chosenTargets.get(possibleTarget).size();

			if(1 == targetCount)
				ret.add(object);
		}

		return ret;
	}

}
