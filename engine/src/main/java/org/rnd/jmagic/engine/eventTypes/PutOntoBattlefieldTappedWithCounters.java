package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOntoBattlefieldTappedWithCounters extends EventType
{	public static final EventType INSTANCE = new PutOntoBattlefieldTappedWithCounters();

	 private PutOntoBattlefieldTappedWithCounters()
	{
		super("PUT_ONTO_BATTLEFIELD_TAPPED_WITH_COUNTERS");
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
		Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
		boolean status = putOntoBattlefield.perform(event, false);

		for(GameObject object: NewObjectOf.instance(putOntoBattlefield.getResultGenerator()).evaluate(game.actualState, null).getAll(GameObject.class))
			if(object.zoneID == game.actualState.battlefield().ID)
				object.getPhysical().setTapped(true);

		java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
		counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
		{
			Set numberParameter = parameters.get(Parameter.NUMBER);
			number = Sum.get(numberParameter);
			counterParameters.put(Parameter.NUMBER, numberParameter);
		}
		Set counterType = parameters.get(Parameter.COUNTER);
		counterParameters.put(Parameter.COUNTER, counterType);
		Set object = NewObjectOf.instance(putOntoBattlefield.getResultGenerator()).evaluate(game.actualState, null);
		counterParameters.put(Parameter.OBJECT, object);
		Event putCounters = createEvent(game, "Put " + number + " " + counterType.getOne(Counter.CounterType.class) + (number == 1 ? "" : "s") + " on " + object + ".", PUT_COUNTERS, counterParameters);
		boolean counterStatus = putCounters.perform(event, false);

		event.setResult(putOntoBattlefield.getResultGenerator());
		return status && counterStatus;
	}
}