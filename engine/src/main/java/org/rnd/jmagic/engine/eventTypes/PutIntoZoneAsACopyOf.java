package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutIntoZoneAsACopyOf extends EventType
{	public static final EventType INSTANCE = new PutIntoZoneAsACopyOf();

	 private PutIntoZoneAsACopyOf()
	{
		super("PUT_INTO_ZONE_AS_A_COPY_OF");
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
		Set cause = parameters.get(Parameter.CAUSE);
		GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		Set to = parameters.get(Parameter.TO);

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, cause);
		moveParameters.put(Parameter.TO, to);
		if(parameters.containsKey(Parameter.CONTROLLER))
			moveParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
		moveParameters.put(Parameter.OBJECT, new Set(object));
		Event move = createEvent(game, "Move " + object + " to " + to, EventType.MOVE_OBJECTS, moveParameters);
		boolean ret = move.perform(event, false);
		if(!ret)
			return false;

		ZoneChange movement = move.getResult().getOne(ZoneChange.class);
		event.setResult(Identity.instance(movement));

		Set source = parameters.get(Parameter.SOURCE);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, NewObjectOf.instance(Identity.instance(movement)));
		part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, Identity.fromCollection(source));
		if(parameters.containsKey(Parameter.TYPE))
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(parameters.get(Parameter.TYPE)));

		EventFactory copy = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, (object + " copies " + source));
		copy.parameters.put(EventType.Parameter.CAUSE, Identity.fromCollection(cause));
		copy.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
		copy.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		movement.events.add(copy);

		return true;
	}
}