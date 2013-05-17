package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutIntoGraveyard extends EventType
{	public static final EventType INSTANCE = new PutIntoGraveyard();

	 private PutIntoGraveyard()
	{
		super("PUT_INTO_GRAVEYARD");
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
			Set mappedSet = null;
			Zone targetGraveyard = object.getOwner(game.actualState).getGraveyard(game.actualState);
			if(objectMap.containsKey(targetGraveyard))
				mappedSet = objectMap.get(targetGraveyard);
			else
				mappedSet = new Set();

			mappedSet.add(object);
			objectMap.put(targetGraveyard, mappedSet);
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

			Event moveEvent = createEvent(game, "Put " + theseObjects + " into " + to + ".", EventType.MOVE_OBJECTS, moveParameters);
			if(!moveEvent.perform(event, false))
				allMoved = false;

			result.addAll(moveEvent.getResult());
		}

		event.setResult(result);
		return allMoved;
	}
}