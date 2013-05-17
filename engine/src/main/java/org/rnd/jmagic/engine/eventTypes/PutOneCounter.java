package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOneCounter extends EventType
{	public static final EventType INSTANCE = new PutOneCounter();

	 private PutOneCounter()
	{
		super("PUT_ONE_COUNTER");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Identified physicalObject = parameters.get(Parameter.OBJECT).getOne(Identified.class).getPhysical();
		Counter newCounter = new Counter(parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class), physicalObject.ID);
		if(physicalObject instanceof GameObject)
			((GameObject)physicalObject).counters.add(newCounter);
		else
			((Player)physicalObject).counters.add(newCounter);

		event.setResult(Identity.instance(newCounter));

		return true;
	}
}