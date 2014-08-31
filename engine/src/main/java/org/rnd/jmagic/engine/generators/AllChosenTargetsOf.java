package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to every chosen Target on the given object. The evaluation will
 * either contain Target objects whose {@link Target#targetID} fields are set,
 * or nothing at all.
 */
public class AllChosenTargetsOf extends SetGenerator
{
	public static AllChosenTargetsOf instance(SetGenerator what)
	{
		return new AllChosenTargetsOf(what);
	}

	private final SetGenerator what;

	private AllChosenTargetsOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set returnValue = new Set();

		Set what = this.what.evaluate(state, thisObject);

		for(GameObject object: what.getAll(GameObject.class))
		{
			java.util.Map<Target, java.util.List<Target>> chosenTargets = object.getChosenTargets();
			for(java.util.List<Target> chosenTarget: chosenTargets.values())
				for(Target t: chosenTarget)
					returnValue.add(t);
		}

		return returnValue;
	}
}
