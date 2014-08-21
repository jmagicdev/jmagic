package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CounterOne extends EventType
{
	public static final EventType INSTANCE = new CounterOne();

	private CounterOne()
	{
		super("COUNTER_ONE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set result = new Set();
		boolean status = true;

		Set counterer = parameters.get(Parameter.CAUSE);
		Zone zone = (parameters.containsKey(Parameter.TO) ? parameters.get(Parameter.TO).getOne(Zone.class) : null);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		if(object.zoneID == game.actualState.stack().ID)
		{
			GameObject countered = (zone == null ? object.counterThisObject(counterer) : object.counterThisObject(counterer, zone));
			result.add(countered);
		}
		else
			status = false;
		event.setResult(Identity.fromCollection(result));

		return status;
	}
}