package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TapPermanents extends EventType
{
	public static final EventType INSTANCE = new TapPermanents();

	private TapPermanents()
	{
		super("TAP_PERMANENTS");
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
			if(object.isTapped())
				return false;

		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();

		boolean allTapped = true;
		for(GameObject actualObject: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> tapOneParameters = new java.util.HashMap<Parameter, Set>();
			tapOneParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			tapOneParameters.put(Parameter.OBJECT, new Set(actualObject));
			Event tapOne = createEvent(game, "Tap " + actualObject + ".", TAP_ONE_PERMANENT, tapOneParameters);

			if(!tapOne.perform(event, false))
				allTapped = false;
			result.addAll(tapOne.getResult());
		}
		event.setResult(Identity.fromCollection(result));
		return allTapped;
	}
}