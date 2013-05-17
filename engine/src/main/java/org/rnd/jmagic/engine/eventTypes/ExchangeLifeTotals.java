package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ExchangeLifeTotals extends EventType
{	public static final EventType INSTANCE = new ExchangeLifeTotals();

	 private ExchangeLifeTotals()
	{
		super("EXCHANGE_LIFE_TOTALS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		return this.attemptEvents(event, this.getEvents(game, parameters));
	}

	private boolean attemptEvents(Event parentEvent, java.util.Set<Event> events)
	{
		if(events == null)
			return false;
		for(Event event: events)
			if(!event.attempt(parentEvent))
				return false;
		return true;
	}

	private java.util.Set<Event> getEvents(Game game, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);

		if(players.size() != 2)
			return null;

		java.util.Iterator<Player> iter = players.iterator();
		Player playerOne = iter.next();
		Player playerTwo = iter.next();
		int lifeOne = playerOne.lifeTotal;
		int lifeTwo = playerTwo.lifeTotal;

		java.util.Map<Parameter, Set> parametersOne = new java.util.HashMap<Parameter, Set>();
		parametersOne.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		parametersOne.put(Parameter.NUMBER, new Set(lifeTwo));
		parametersOne.put(Parameter.PLAYER, new Set(playerOne));
		Event eventOne = createEvent(game, playerOne + "'s life total becomes " + lifeTwo, EventType.SET_LIFE, parametersOne);

		java.util.Map<Parameter, Set> parametersTwo = new java.util.HashMap<Parameter, Set>();
		parametersTwo.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		parametersTwo.put(Parameter.NUMBER, new Set(lifeOne));
		parametersTwo.put(Parameter.PLAYER, new Set(playerTwo));
		Event eventTwo = createEvent(game, playerTwo + "'s life total becomes " + lifeOne, EventType.SET_LIFE, parametersTwo);

		java.util.Set<Event> ret = new java.util.HashSet<Event>();
		ret.add(eventOne);
		ret.add(eventTwo);
		return ret;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		java.util.Set<Event> events = this.getEvents(game, parameters);
		if(!this.attemptEvents(event, events))
			return false;

		boolean ret = true;

		for(Event childEvent: events)
			if(!childEvent.perform(event, false))
				ret = false;

		return ret;
	}
}