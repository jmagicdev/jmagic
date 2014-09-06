package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all things targeting the specified objects or players (or zones
 * circu go fuck yourself)
 */
public class HasTarget extends SetGenerator
{
	public static HasTarget instance(SetGenerator what)
	{
		return new HasTarget(what);
	}

	private SetGenerator what;

	private HasTarget(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{

		java.util.Set<Integer> ids = new java.util.HashSet<Integer>();
		for(Identified id: this.what.evaluate(state, thisObject).getAll(Identified.class))
			ids.add(id.ID);
		java.util.Set<Integer> retIDs = new java.util.HashSet<Integer>();
		for(GameObject object: state.getAll(GameObject.class))
		{
			java.util.List<Mode>[] modes = object.getModes();
			for(int sideNumber = 0; sideNumber < modes.length; ++sideNumber)
				for(int modeNumber = 1; modeNumber <= modes[sideNumber].size(); modeNumber++)
					if(object.getSelectedModeNumbers()[sideNumber].contains(modeNumber))
						for(Target possibleTarget: object.getMode(modeNumber).targets)
							if(object.getChosenTargets()[sideNumber].containsKey(possibleTarget))
								for(Target chosenTarget: object.getChosenTargets()[sideNumber].get(possibleTarget))
									if(ids.contains(chosenTarget.targetID))
										retIDs.add(object.ID);
		}
		return IdentifiedWithID.instance(retIDs).evaluate(state, thisObject);
	}

}
