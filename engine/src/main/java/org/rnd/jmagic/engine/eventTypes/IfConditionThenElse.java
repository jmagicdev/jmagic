package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class IfConditionThenElse extends EventType
{	public static final EventType INSTANCE = new IfConditionThenElse();

	 private IfConditionThenElse()
	{
		super("IF_CONDITION_THEN_ELSE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.IF;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set ifCondition = parameters.get(Parameter.IF);

		// if condition is true, do Then
		if(!ifCondition.isEmpty())
		{
			// if there isn't a then event, return FULL
			if(!parameters.containsKey(Parameter.THEN))
			{
				event.setResult(Empty.set);
				return true;
			}

			Event thenEvent = parameters.get(Parameter.THEN).getOne(EventFactory.class).createEvent(game, event.getSource());
			boolean status = thenEvent.perform(event, false);
			event.setResult(thenEvent.getResultGenerator());
			return status;
		}

		// otherwise condition is false, so do Else

		// if there isn't an else event, return NONE
		if(!parameters.containsKey(Parameter.ELSE))
		{
			event.setResult(Empty.set);
			return false;
		}

		Event elseEvent = parameters.get(Parameter.ELSE).getOne(EventFactory.class).createEvent(game, event.getSource());
		boolean status = elseEvent.perform(event, false);
		event.setResult(elseEvent.getResultGenerator());
		return status;
	}
}