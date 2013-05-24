package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CopySpellForEachTarget extends EventType
{	public static final EventType INSTANCE = new CopySpellForEachTarget();

	 private CopySpellForEachTarget()
	{
		super("COPY_SPELL_FOR_EACH_TARGET");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean ret = true;

		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

		event.setResult(Empty.set);

		Target toChange = null;

		for(java.util.Map.Entry<Target, java.util.List<Target>> entry: object.getChosenTargets().entrySet())
		{
			// This eventtype doesn't work on objects with multiple
			// targets
			if(entry.getValue().size() > 1)
				return false;

			if(toChange == null)
				toChange = entry.getValue().get(0);
			else
				// This eventtype doesn't work on objects with multiple
				// targets
				return false;
		}

		Set targets = toChange.legalChoicesNow(game, object);
		targets.remove(game.actualState.get(toChange.targetID));

		if(parameters.containsKey(Parameter.TARGET))
			targets = Intersect.get(targets, parameters.get(Parameter.TARGET));

		java.util.Map<Parameter, Set> copyParameters = new java.util.HashMap<Parameter, Set>();
		copyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		copyParameters.put(Parameter.PLAYER, new Set(object.getController(game.actualState)));
		copyParameters.put(Parameter.OBJECT, new Set(object));
		copyParameters.put(Parameter.TARGET, Empty.set);
		copyParameters.put(Parameter.NUMBER, new Set(targets.size()));
		Event copyEvent = createEvent(game, "Copy the spell for each other possible target.", EventType.COPY_SPELL_OR_ABILITY, copyParameters);
		copyEvent.perform(event, false);

		java.util.Iterator<Identified> targetsIter = targets.getAll(Identified.class).iterator();

		java.util.Set<GameObject> copies = copyEvent.getResult().getAll(GameObject.class);
		for(GameObject copy: copies)
		{
			Identified nextTarget = targetsIter.next();
			for(java.util.List<Target> chosenTargets: copy.getChosenTargets().values())
				chosenTargets.get(0).targetID = nextTarget.ID;
		}

		if(targets.size() > 1)
		{
			Zone stack = game.physicalState.stack();
			java.util.List<GameObject> orderedObjects = object.getController(game.actualState).sanitizeAndChoose(game.actualState, copies.size(), copies, PlayerInterface.ChoiceType.MOVEMENT_STACK, PlayerInterface.ChooseReason.ORDER_STACK);
			for(GameObject copy: orderedObjects)
				if(stack.objects.remove(copy))
					stack.addToTop(copy);
		}

		return ret;
	}
}