package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the result of each of the given events
 */
public class EventResult extends SetGenerator
{
	public static EventResult instance(SetGenerator what)
	{
		return new EventResult(what);
	}

	private final SetGenerator events;

	private EventResult(SetGenerator events)
	{
		this.events = events;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Event event: this.events.evaluate(state, thisObject).getAll(Event.class))
			ret.addAll(event.getResult(state));
		return ret;
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		java.util.Set<ManaSymbol.ManaType> ret = new java.util.HashSet<ManaSymbol.ManaType>();

		for(Event event: this.events.evaluate(game, null).getAll(Event.class))
		{
			if(event.parameters.containsKey(EventType.Parameter.CHOICE))
			{
				Set choices = event.parameters.get(EventType.Parameter.CHOICE).evaluate(game, thisObject);

				ret.addAll(choices.getAll(ManaSymbol.ManaType.class));

				for(Color c: choices.getAll(Color.class))
					ret.add(c.getManaType());

				for(ManaSymbol symbol: choices.getAll(ManaSymbol.class))
				{
					if(symbol.colorless > 0)
						ret.add(ManaSymbol.ManaType.COLORLESS);
					for(Color c: symbol.colors)
						ret.add(c.getManaType());
				}
			}
		}

		return ret;
	}
}
