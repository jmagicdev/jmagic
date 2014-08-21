package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOntoBattlefield extends EventType
{
	public static final EventType INSTANCE = new PutOntoBattlefield();

	private PutOntoBattlefield()
	{
		super("PUT_ONTO_BATTLEFIELD");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			if(object.isGhost())
				return false;
		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
		newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		newParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
		newParameters.put(Parameter.ZONE, new Set(game.physicalState.battlefield()));
		newParameters.put(Parameter.OBJECT, parameters.get(Parameter.OBJECT));
		if(parameters.containsKey(Parameter.RESOLVING))
			newParameters.put(Parameter.RESOLVING, Empty.set);

		Event move = createEvent(game, event.getName(), PUT_IN_CONTROLLED_ZONE, newParameters);
		boolean status = move.perform(event, false);
		event.setResult(move.getResultGenerator());
		return status;
	}
}