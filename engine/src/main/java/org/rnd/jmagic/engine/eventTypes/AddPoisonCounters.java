package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AddPoisonCounters extends EventType
{	public static final EventType INSTANCE = new AddPoisonCounters();

	 private AddPoisonCounters()
	{
		super("ADD_POISON_COUNTERS");
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
			for(int i = 0; i < number; ++i)
			{
				Counter counter = new Counter(Counter.CounterType.POISON, player.ID);
				if(physical.counters.add(counter))
					counters.add(counter);
			}
		}
		event.setResult(Identity.fromCollection(counters));
		return true;
	}
}