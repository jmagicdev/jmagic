package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutIntoLibrary extends EventType
{
	public static final EventType INSTANCE = new PutIntoLibrary();

	private PutIntoLibrary()
	{
		super("PUT_INTO_LIBRARY");
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

		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		if(objects.isEmpty())
		{
			event.setResult(Empty.set);
			return true;
		}

		java.util.Map<Zone, Set> objectMap = new java.util.HashMap<Zone, Set>();
		for(GameObject object: objects)
		{
			Zone targetLibrary = object.getOwner(game.actualState).getLibrary(game.actualState);
			if(!objectMap.containsKey(targetLibrary))
				objectMap.put(targetLibrary, new Set());
			objectMap.get(targetLibrary).add(object);
		}

		Set result = new Set();
		for(java.util.Map.Entry<Zone, Set> toEntry: objectMap.entrySet())
		{
			Zone to = toEntry.getKey();
			Set theseObjects = toEntry.getValue();

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.TO, new Set(to));
			moveParameters.put(Parameter.OBJECT, theseObjects);
			if(parameters.containsKey(Parameter.INDEX))
				moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));
			if(parameters.containsKey(Parameter.RANDOM))
				moveParameters.put(Parameter.RANDOM, Empty.set);

			Event moveEvent = createEvent(game, "Put " + theseObjects + " into " + to + ".", EventType.MOVE_OBJECTS, moveParameters);
			if(!moveEvent.perform(event, false))
				allMoved = false;

			result.addAll(moveEvent.getResult());
		}

		event.setResult(result);
		return allMoved;
	}
}