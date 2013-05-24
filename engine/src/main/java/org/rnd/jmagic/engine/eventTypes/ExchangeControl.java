package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ExchangeControl extends EventType
{	public static final EventType INSTANCE = new ExchangeControl();

	 private ExchangeControl()
	{
		super("EXCHANGE_CONTROL");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		return this.attemptEvents(event, this.getEvents(game, parameters));
	}

	private boolean attemptEvents(Event parentEvent, java.util.Set<Event> events)
	{
		if(events == null)
			return false;
		for(Event event: events)
			if(!event.attempt(parentEvent))
				return false;
		return true;
	}

	private java.util.Set<Event> getEvents(Game game, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);

		if(objects.size() != 2)
			return null;

		java.util.Set<Event> ret = new java.util.HashSet<Event>();
		java.util.Iterator<GameObject> iter = objects.iterator();
		GameObject objectOne = iter.next();
		GameObject objectTwo = iter.next();
		Player controllerOne = objectOne.getController(game.actualState);
		Player controllerTwo = objectTwo.getController(game.actualState);

		{
			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(objectTwo));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(controllerOne));

			java.util.Map<Parameter, Set> controlParameters = new java.util.HashMap<Parameter, Set>();
			controlParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			controlParameters.put(Parameter.EFFECT, new Set(controlPart));
			controlParameters.put(Parameter.EXPIRES, new Set(Empty.instance()));
			ret.add(createEvent(game, controllerOne + " gains control of " + objectTwo + ".", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, controlParameters));
		}

		{
			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(objectOne));
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(controllerTwo));

			java.util.Map<Parameter, Set> controlParameters = new java.util.HashMap<Parameter, Set>();
			controlParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			controlParameters.put(Parameter.EFFECT, new Set(controlPart));
			controlParameters.put(Parameter.EXPIRES, new Set(Empty.instance()));
			ret.add(createEvent(game, controllerTwo + " gains control of " + objectOne + ".", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, controlParameters));
		}

		return ret;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		java.util.Set<Event> events = this.getEvents(game, parameters);
		if(!this.attemptEvents(event, events))
			return false;

		boolean ret = true;

		for(Event childEvent: events)
			if(!childEvent.perform(event, false))
				ret = false;

		return ret;
	}
}