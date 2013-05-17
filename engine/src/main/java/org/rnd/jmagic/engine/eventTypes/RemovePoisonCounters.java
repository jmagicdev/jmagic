package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RemovePoisonCounters extends EventType
{	public static final EventType INSTANCE = new RemovePoisonCounters();

	 private RemovePoisonCounters()
	{
		super("REMOVE_POISON_COUNTERS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<Counter> counters = new java.util.HashSet<Counter>();
		int number = Sum.get(parameters.get(Parameter.NUMBER));
		java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);
		for(Player player: players)
		{
			Player physical = player.getPhysical();
			java.util.Iterator<Counter> i = physical.counters.iterator();
			int removed = 0;
			while(i.hasNext() && (removed < number))
			{
				Counter c = i.next();
				if(Counter.CounterType.POISON == c.getType())
				{
					i.remove();
					counters.add(c);
					++removed;
				}
			}
		}
		event.setResult(Identity.instance(counters));
		if(counters.size() == players.size() * number)
			return true;

		return false;
	}
}