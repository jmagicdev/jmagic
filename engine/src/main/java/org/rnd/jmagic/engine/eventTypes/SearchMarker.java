package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class SearchMarker extends EventType
{
	public static final EventType INSTANCE = new SearchMarker();

	private SearchMarker()
	{
		super("SEARCH_MARKER");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(parameters.get(Parameter.CARD));
		return true;
	}
};