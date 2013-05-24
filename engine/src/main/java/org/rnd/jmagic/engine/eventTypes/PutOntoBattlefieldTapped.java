package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOntoBattlefieldTapped extends EventType
{	public static final EventType INSTANCE = new PutOntoBattlefieldTapped();

	 private PutOntoBattlefieldTapped()
	{
		super("PUT_ONTO_BATTLEFIELD_TAPPED");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			if(object.isGhost())
				return false;
		return true;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
		boolean status = putOntoBattlefield.perform(event, false);

		for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
		{
			EventFactory tap = new EventFactory(TAP_PERMANENTS, event.getName());
			tap.parameters.put(EventType.Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
			tap.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
			change.events.add(tap);
		}

		event.setResult(putOntoBattlefield.getResultGenerator());
		return status;
	}
}