package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DestroyPermanents extends EventType
{	public static final EventType INSTANCE = new DestroyPermanents();

	 private DestroyPermanents()
	{
		super("DESTROY_PERMANENTS");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PERMANENT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		boolean allDestroyed = true;
		Set cause = parameters.get(Parameter.CAUSE);
		Set result = new Set();
		for(GameObject object: parameters.get(Parameter.PERMANENT).getAll(GameObject.class))
		{
			if(object.isPermanent())
			{
				java.util.Map<Parameter, Set> destroyParameters = new java.util.HashMap<Parameter, Set>();
				destroyParameters.put(Parameter.CAUSE, cause);
				destroyParameters.put(Parameter.PERMANENT, new Set(object));
				Event destroy = createEvent(game, "Destroy " + object + ".", EventType.DESTROY_ONE_PERMANENT, destroyParameters);
				if(!destroy.perform(event, false))
					allDestroyed = false;
				result.addAll(destroy.getResult());
			}
			else
				allDestroyed = false;
		}

		event.setResult(Identity.instance(result));
		return allDestroyed;
	}
}