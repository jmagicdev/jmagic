package org.rnd.jmagic.engine.eventTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RemoveCountersFromChoice extends EventType
{
	public static final EventType INSTANCE = new RemoveCountersFromChoice();

	private RemoveCountersFromChoice()
	{
		super("REMOVE_COUNTERS_FROM_CHOICE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Counter.CounterType type = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = parameters.get(Parameter.NUMBER).getOne(Integer.class);

		for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			int counters = 0;
			for(Counter c: o.counters)
				if(c.getType() == type && ++counters >= number)
					return true;
		}
		return false;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
		Counter.CounterType type = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
		int number = 1;
		if(parameters.containsKey(Parameter.NUMBER))
			number = parameters.get(Parameter.NUMBER).getOne(Integer.class);

		java.util.Set<GameObject> objects = new java.util.HashSet<GameObject>();
		for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			int counters = 0;
			for(Counter c: o.counters)
				if(c.getType() == type && ++counters >= number)
				{
					objects.add(o);
					break;
				}
		}
		if(objects.isEmpty())
		{
			event.setResult(Empty.set);
			return false;
		}

		GameObject choice = player.sanitizeAndChoose(game.actualState, 1, objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.REMOVE_COUNTER_FROM).get(0);

		Event removeCounters = removeCounters(number, type, Identity.instance(choice), "Remove " + number + " " + type + "(s) from " + choice).createEvent(game, event.getSource());
		boolean ret = removeCounters.perform(event, false);
		event.setResult(removeCounters.getResult());
		return ret;
	}
}