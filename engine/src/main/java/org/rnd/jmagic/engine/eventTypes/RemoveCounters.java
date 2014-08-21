package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RemoveCounters extends EventType
{
	public static final EventType INSTANCE = new RemoveCounters();

	private RemoveCounters()
	{
		super("REMOVE_COUNTERS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<Counter.CounterType> counterTypes = parameters.get(Parameter.COUNTER).getAll(Counter.CounterType.class);
		Set objects = parameters.get(Parameter.OBJECT);
		if(!parameters.containsKey(Parameter.NUMBER))
			return true;

		int number = Sum.get(parameters.get(Parameter.NUMBER));

		for(GameObject object: objects.getAll(GameObject.class))
			for(Counter.CounterType type: counterTypes)
				if(CountersOn.get(object, type).size() < number)
					return false;

		for(Player player: objects.getAll(Player.class))
			for(Counter.CounterType type: counterTypes)
				if(CountersOn.get(player, type).size() < number)
					return false;

		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();
		java.util.Set<Counter.CounterType> counterTypes = parameters.get(Parameter.COUNTER).getAll(Counter.CounterType.class);
		java.util.Set<Identified> objects = parameters.get(Parameter.OBJECT).getAll(Identified.class);
		Integer number = null;
		if(parameters.containsKey(Parameter.NUMBER))
			number = Sum.get(parameters.get(Parameter.NUMBER));
		boolean allRemoved = true;

		for(Counter.CounterType counterType: counterTypes)
		{
			for(Identified object: objects)
			{
				int numCounters = (object.isPlayer() ? CountersOn.get((Player)object, counterType) : CountersOn.get((GameObject)object, counterType)).size();
				int toRemove = number == null ? numCounters : number;
				for(int numRemoved = 0; numRemoved < toRemove; numRemoved++)
				{
					java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
					counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					counterParameters.put(Parameter.COUNTER, new Set(counterType));
					counterParameters.put(Parameter.OBJECT, new Set(object));
					Event removeCounter = createEvent(game, "Remove a " + counterType + " from " + object + ".", EventType.REMOVE_ONE_COUNTER, counterParameters);
					boolean status = removeCounter.perform(event, false);
					if(!status)
						allRemoved = false;
					result.addAll(removeCounter.getResult());
				}
			}
		}

		event.setResult(Identity.fromCollection(result));
		return allRemoved;
	}
}