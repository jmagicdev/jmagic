package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOntoBattlefieldWithCounters extends EventType
{	public static final EventType INSTANCE = new PutOntoBattlefieldWithCounters();

	 private PutOntoBattlefieldWithCounters()
	{
		super("PUT_ONTO_BATTLEFIELD_WITH_COUNTERS");
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
		boolean moveStatus = putOntoBattlefield.perform(event, false);

		for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
		{
			Set counterType = parameters.get(Parameter.COUNTER);
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));
			EventFactory putCounters = new EventFactory(PUT_COUNTERS, "Put " + number + " " + counterType.getOne(Counter.CounterType.class) + (number == 1 ? "" : "s") + " on " + parameters.get(Parameter.OBJECT).getAll(GameObject.class));
			putCounters.parameters.put(Parameter.CAUSE, Identity.fromCollection(parameters.get(Parameter.CAUSE)));
			putCounters.parameters.put(Parameter.COUNTER, Identity.fromCollection(counterType));
			putCounters.parameters.put(Parameter.NUMBER, Identity.instance(number));
			putCounters.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
			change.events.add(putCounters);
		}

		event.setResult(putOntoBattlefield.getResultGenerator());
		return moveStatus;
	}
}