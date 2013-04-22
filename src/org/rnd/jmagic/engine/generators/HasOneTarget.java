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
			int targetCount = 0;
			for(Mode mode: object.getSelectedModes())
				for(Target possibleTarget: mode.targets)
					if(object.getChosenTargets().containsKey(possibleTarget))
						targetCount += object.getChosenTargets().get(possibleTarget).size();

			if(1 == targetCount)
				ret.add(object);
		}

		return ret;
	}

}
