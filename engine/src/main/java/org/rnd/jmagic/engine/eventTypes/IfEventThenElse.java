package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class IfEventThenElse extends EventType
{	public static final EventType INSTANCE = new IfEventThenElse();

	 private IfEventThenElse()
	{
		super("IF_EVENT_THEN_ELSE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.IF;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Event ifEvent = parameters.get(Parameter.IF).getOne(EventFactory.class).createEvent(game, event.getSource());
		ifEvent.isCost = true;
		boolean ifStatus = ifEvent.perform(event, true);

		if(ifStatus)
		{
			// if there isn't a then event, return FULL
			if(!parameters.containsKey(Parameter.THEN))
			{
				event.setResult(Empty.set);
				return true;
			}

			Event thenEvent = parameters.get(Parameter.THEN).getOne(EventFactory.class).createEvent(game, event.getSource());
			boolean status = thenEvent.perform(event, true);
			Set result = thenEvent.getResult();
			event.setResult(Identity.fromCollection(result));
			return status;
		}
		// if there isn't an else event, return NONE
		if(!parameters.containsKey(Parameter.ELSE))
		{
			event.setResult(Empty.set);
			return false;
		}

		Event elseEvent = parameters.get(Parameter.ELSE).getOne(EventFactory.class).createEvent(game, event.getSource());
		boolean status = elseEvent.perform(event, true);
		Set result = elseEvent.getResult();
		event.setResult(Identity.fromCollection(result));
		return status;
	}
}