package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutInControlledZone extends EventType
{
	public static final EventType INSTANCE = new PutInControlledZone();

	private PutInControlledZone()
	{
		super("PUT_IN_CONTROLLED_ZONE");
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
		boolean allMoved = true;

		Set objects = parameters.get(Parameter.OBJECT);
		if(objects.isEmpty())
		{
			event.setResult(Empty.set);
			return true;
		}

		boolean resolvingParameterPresent = parameters.containsKey(Parameter.RESOLVING);

		boolean faceDownParameterPresent = parameters.containsKey(Parameter.FACE_DOWN);
		Set faceDownParameter = null;
		if(faceDownParameterPresent)
			faceDownParameter = parameters.get(Parameter.FACE_DOWN);

		Set cause = parameters.get(Parameter.CAUSE);
		Set controller = parameters.get(Parameter.CONTROLLER);
		Zone controlledZone = parameters.get(Parameter.ZONE).getOne(Zone.class);
		Set result = new Set();

		java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
		moveParameters.put(Parameter.CAUSE, cause);
		moveParameters.put(Parameter.TO, new Set(controlledZone));
		moveParameters.put(Parameter.OBJECT, objects);
		moveParameters.put(Parameter.CONTROLLER, controller);
		if(parameters.containsKey(Parameter.INDEX))
			moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));
		if(parameters.containsKey(Parameter.EFFECT))
			moveParameters.put(Parameter.EFFECT, parameters.get(Parameter.EFFECT));
		if(resolvingParameterPresent)
			moveParameters.put(Parameter.RESOLVING, Empty.set);
		if(faceDownParameterPresent)
			moveParameters.put(Parameter.FACE_DOWN, faceDownParameter);

		Event moveEvent = createEvent(game, "Put " + objects + " onto " + controlledZone + ".", EventType.MOVE_OBJECTS, moveParameters);
		if(!moveEvent.perform(event, false))
			allMoved = false;

		result.addAll(moveEvent.getResult());

		event.setResult(result);
		return allMoved;
	}
}