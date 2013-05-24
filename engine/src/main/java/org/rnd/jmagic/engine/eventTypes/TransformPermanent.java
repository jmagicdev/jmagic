package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TransformPermanent extends EventType
{	public static final EventType INSTANCE = new TransformPermanent();

	 private TransformPermanent()
	{
		super("TRANSFORM_PERMANENT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			java.util.Map<Parameter, Set> transformOneParameters = new java.util.HashMap<EventType.Parameter, Set>();
			transformOneParameters.put(Parameter.OBJECT, new Set(object));
			createEvent(game, "Transform " + object, TRANSFORM_ONE_PERMANENT, transformOneParameters).perform(event, false);
		}

		event.setResult(Empty.set);
		return true;
	}
}