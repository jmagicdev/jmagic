package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to every Target of the given object. If mode indices are specified,
 * only those modes' targets. Otherwise, the targets of every selected mode.
 */
public class AllTargetsOf extends SetGenerator
{
	public static AllTargetsOf instance(SetGenerator what)
	{
		return new AllTargetsOf(what, ALL_SELECTED_MODES);
	}

	public static AllTargetsOf instance(SetGenerator what, SetGenerator modeIndices)
	{
		return new AllTargetsOf(what, modeIndices);
	}

	private static final int ALL_SELECTED_MODES_VALUE = -1;
	public static final SetGenerator ALL_SELECTED_MODES = Identity.instance(ALL_SELECTED_MODES_VALUE);

	private final SetGenerator modeIndices;
	private final SetGenerator what;

	private AllTargetsOf(SetGenerator what, SetGenerator modeIndices)
	{
		this.modeIndices = modeIndices;
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set returnValue = new Set();

		Set what = this.what.evaluate(state, thisObject);
		java.util.Set<Integer> modeIndices = this.modeIndices.evaluate(state, thisObject).getAll(Integer.class);

		for(GameObject object: what.getAll(GameObject.class))
			for(Integer modeNum: modeIndices)
				if(ALL_SELECTED_MODES_VALUE == modeNum)
				{
					for(Mode mode: object.getSelectedModes())
						for(Target possibleTarget: mode.targets)
							if(object.getChosenTargets().containsKey(possibleTarget))
								for(Target chosenTarget: object.getChosenTargets().get(possibleTarget))
									if(null != chosenTarget)
										returnValue.add(chosenTarget);
				}
				else
				{
					Mode mode = object.getMode(modeNum);
					for(Target possibleTarget: mode.targets)
						if(object.getChosenTargets().containsKey(possibleTarget))
							for(Target chosenTarget: object.getChosenTargets().get(possibleTarget))
								if(null != chosenTarget)
									returnValue.add(chosenTarget);
				}
		return returnValue;
	}
}
