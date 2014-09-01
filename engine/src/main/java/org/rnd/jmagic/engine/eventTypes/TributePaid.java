package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;

public final class TributePaid extends EventType
{
	public static final EventType INSTANCE = new TributePaid();

	private TributePaid()
	{
		super("TRIBUTE_PAID");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(parameters.get(Parameter.OBJECT));
		return true;
	}
}