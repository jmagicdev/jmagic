package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class MoveObjects extends EventType
{
	public static final EventType INSTANCE = new MoveObjects();

	private MoveObjects()
	{
		super("MOVE_OBJECTS");
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
		if(objects.isEmpty())
		{
			event.setResult(Empty.set);
			return true;
		}

		Set cause = parameters.get(Parameter.CAUSE);
		Zone to = parameters.get(Parameter.TO).getOne(Zone.class);
		int controllerID = -1;
		if(parameters.containsKey(Parameter.CONTROLLER))
			controllerID = parameters.get(Parameter.CONTROLLER).getOne(Player.class).ID;

		java.util.Set<Integer> characteristicsIndices = null;
		if(parameters.containsKey(Parameter.EFFECT))
			characteristicsIndices = new java.util.HashSet<>(parameters.get(Parameter.EFFECT).getAll(Integer.class));

		int index = 0;
		if(parameters.containsKey(Parameter.INDEX))
			index = parameters.get(Parameter.INDEX).getOne(Integer.class);
		Class<? extends Characteristics> faceDownCharacteristics = null;
		if(parameters.containsKey(Parameter.FACE_DOWN))
			faceDownCharacteristics = parameters.get(Parameter.FACE_DOWN).getOne(Class.class);
		boolean hidden = parameters.containsKey(Parameter.HIDDEN);
		boolean random = parameters.containsKey(Parameter.RANDOM);
		boolean isSpellResolution = parameters.containsKey(Parameter.RESOLVING);

		GameObject causeObject = cause.getOne(GameObject.class);
		if(null == causeObject && null == cause.getOne(Game.class))
			throw new UnsupportedOperationException("MOVE_OBJECTS has non-GameObject, non-Game cause: " + cause);
		int causeID = causeObject == null ? 0 : causeObject.ID;

		boolean fromLibrary = false;
		boolean allMoved = true;
		Set result = new Set();

		for(GameObject moveMe: objects)
		{
			Zone from = moveMe.getZone().getActual();
			to = to.getActual();

			if(!fromLibrary)
				fromLibrary = from.isLibrary();

			// 110.5f A token that has left the battlefield can't change
			// zones.
			if(moveMe.isToken() && ((Token)moveMe).wasOnBattlefield())
			{
				allMoved = false;
				continue;
			}

			ZoneChange change = new ZoneChange();
			change.causeID = causeID;
			change.characteristicsIndices = characteristicsIndices;
			change.controllerID = controllerID;
			change.destinationZoneID = to.ID;
			change.faceDownCharacteristics = faceDownCharacteristics;
			change.hidden = hidden;
			change.index = index;
			change.isCost = event.isCost;
			change.isEffect = event.isEffect;
			change.isSpellResolution = isSpellResolution;
			change.oldObjectID = moveMe.ID;
			change.random = random;
			change.replacedBy.addAll(event.replacedBy);
			change.sourceZoneID = from.ID;
			event.addZoneChange(change);

			result.add(change);
		}

		event.setResult(Identity.fromCollection(result));

		to = to.getActual();
		if((game.currentAction != null) && !parameters.get(Parameter.OBJECT).isEmpty())
			if(to.isLibrary() || (fromLibrary && game.actualState.stack().equals(to)))
				game.currentAction.irreversible = true;

		return allMoved;
	}
}