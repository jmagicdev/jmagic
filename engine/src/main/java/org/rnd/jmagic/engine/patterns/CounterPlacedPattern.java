package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class CounterPlacedPattern implements EventPattern
{
	private Counter.CounterType type;
	private SetPattern objectFilter;

	/**
	 * Creates an EventPattern matching one counter being placed on a permanent,
	 * as per the following rule:
	 * 
	 * 121.6. If a spell or ability refers to a counter being "placed" on a
	 * permanent, it means putting a counter on that permanent while it's on the
	 * battlefield, or that permanent entering the battlefield with a counter on
	 * it as the result of a replacement effect (see rule 614.1c).
	 * 
	 * @param type The type of counter to match against (null for "any").
	 * @param objectFilter what objects to match against (null for "any"). Don't
	 * require that these objects be permanents!
	 */
	public CounterPlacedPattern(Counter.CounterType type, SetGenerator objectFilter)
	{
		this(type, (objectFilter == null ? null : new SimpleSetPattern(objectFilter)));
	}

	/**
	 * Creates an EventPattern matching one counter being placed on a permanent,
	 * as per the following rule:
	 * 
	 * 121.6. If a spell or ability refers to a counter being "placed" on a
	 * permanent, it means putting a counter on that permanent while it's on the
	 * battlefield, or that permanent entering the battlefield with a counter on
	 * it as the result of a replacement effect (see rule 614.1c).
	 * 
	 * @param type The type of counter to match against (null for "any").
	 * @param objectFilter what objects to match against (null for "any"). Don't
	 * require that these objects be permanents!
	 */
	public CounterPlacedPattern(Counter.CounterType type, SetPattern objectFilter)
	{
		this.type = type;
		this.objectFilter = objectFilter;
	}

	@Override
	public boolean match(Event event, Identified object, GameState state)
	{
		if(event.type != EventType.PUT_ONE_COUNTER)
			return false;

		if(this.type != null)
		{
			Counter.CounterType type = event.parameters.get(EventType.Parameter.COUNTER).evaluate(state, object).getOne(Counter.CounterType.class);
			if(type != this.type)
				return false;
		}

		if(this.objectFilter != null)
		{
			Set gettingCounters = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, object);
			if(!this.objectFilter.match(state, object, gettingCounters))
				return false;
		}

		return true;
	}

	@Override
	public boolean looksBackInTime()
	{
		return false;
	}

	@Override
	public boolean matchesManaAbilities()
	{
		return false;
	}
}
