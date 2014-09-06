package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ChangeTargets extends EventType
{
	public static final EventType INSTANCE = new ChangeTargets();

	private ChangeTargets()
	{
		super("CHANGE_TARGETS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		if(parameters.get(Parameter.PLAYER).getOne(Player.class) == null)
			return false;

		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			if(!this.canBeChanged(game, object))
				return false;
		return true;
	}

	private boolean canBeChanged(Game game, GameObject originalObject)
	{
		java.util.Map<Target, java.util.List<Target>> chosenTargets = new java.util.HashMap<>();
		for(java.util.Map<Target, java.util.List<Target>> characteristicChosenTargets: originalObject.getChosenTargets())
			chosenTargets.putAll(characteristicChosenTargets);

		for(Mode mode: originalObject.getSelectedModes())
		{
			java.util.List<Integer> ignoreThese = new java.util.LinkedList<Integer>();
			ignoreThese.add(originalObject.ID);

			for(Target possibleTarget: mode.targets)
			{
				if(!chosenTargets.containsKey(possibleTarget))
					continue;

				for(Target chosenTarget: chosenTargets.get(possibleTarget))
				{
					int previousTarget = chosenTarget.targetID;

					Set legalTargetsNow = chosenTarget.legalChoicesNow(game, originalObject);
					java.util.Set<Target> targetSet = new java.util.HashSet<Target>();

					for(Identified targetObject: legalTargetsNow.getAll(Identified.class))
						if(!ignoreThese.contains(targetObject.ID) && previousTarget != targetObject.ID)
							targetSet.add(new Target(targetObject, chosenTarget));

					if(targetSet.size() == 0)
						return false;

					if(chosenTarget.restrictFromLaterTargets)
						ignoreThese.add(targetSet.iterator().next().targetID);
				}
			}
		}
		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);

		Set result = new Set();
		boolean ret = true;

		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			// if the object can be changed and is changed successfully, add
			// it to results. Otherwise, we're returning false.
			if(this.canBeChanged(game, object) && object.reselectTargets(chooser))
			{
				// Update the physical object, unless this is the physical
				// object.
				for(GameObject objectToUpdate: object.andPhysical())
				{
					if(objectToUpdate == object)
						continue;

					java.util.Map<Target, java.util.List<Target>>[] chosenTargets = object.getChosenTargets();

					for(int i = 0; i < chosenTargets.length; ++i)
					{
						objectToUpdate.getChosenTargets()[i].clear();
						objectToUpdate.getChosenTargets()[i].putAll(object.getChosenTargets()[i]);
					}
				}
				result.add(object);
			}
			else
				ret = false;
		}

		event.setResult(Identity.fromCollection(result));

		return ret;
	}
}