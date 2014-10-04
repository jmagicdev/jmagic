package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ExileUntil extends EventType
{
	public static final EventType INSTANCE = new ExileUntil();

	private ExileUntil()
	{
		super("EXILE_UNTIL");
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
		{
			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			newParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
			newParameters.put(Parameter.OBJECT, new Set(object));
			if(!createEvent(game, "Exile " + object + ".", MOVE_OBJECTS, newParameters).attempt(event))
				return false;
		}

		return false;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// first we have to make sure the return "trigger" hasn't already
		// happened. if it has, we do nothing.
		GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
		SetGenerator returnCondition = parameters.get(Parameter.EXPIRES).getOne(SetGenerator.class);
		if(!returnCondition.evaluate(game, cause).isEmpty())
		{
			event.setResult(Empty.set);
			return true;
		}

		// exile the object(s).
		Set toExile = parameters.get(Parameter.OBJECT);
		if(toExile.isEmpty())
			return event.allChoicesMade;

		Zone returnTo = toExile.getOne(GameObject.class).getZone();

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, new Set(cause));
		moveParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
		moveParameters.put(Parameter.OBJECT, toExile);
		if(parameters.containsKey(Parameter.HIDDEN))
			moveParameters.put(Parameter.HIDDEN, Empty.set);

		Event exileEvent = createEvent(game, "Exile " + toExile + ".", MOVE_OBJECTS, moveParameters);
		boolean status = exileEvent.perform(event, false);

		Identity exileZC = exileEvent.getResultGenerator();
		event.setResult(exileZC);

		// set up the event to return the object(s).
		SetGenerator exiled = NewObjectOf.instance(exileZC);
		EventFactory returnObjects = new EventFactory(EventType.MOVE_OBJECTS, "Return object(s) exiled by " + cause + " to " + returnTo + ".");
		returnObjects.parameters.put(Parameter.CAUSE, Identity.instance(cause));
		returnObjects.parameters.put(Parameter.OBJECT, exiled);
		returnObjects.parameters.put(Parameter.TO, Identity.instance(returnTo));
		if(returnTo.equals(game.actualState.battlefield()))
			returnObjects.parameters.put(Parameter.CONTROLLER, OwnerOf.instance(exiled));
		game.physicalState.addDelayedOneShot(cause, returnCondition, returnObjects);

		return event.allChoicesMade && status;
	}
}