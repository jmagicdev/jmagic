package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class SetAttackingID extends EventType
{
	public static final EventType INSTANCE = new SetAttackingID();

	private SetAttackingID()
	{
		super("SET_ATTACKING_ID");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		event.setResult(Empty.set);

		int attackingID = parameters.get(Parameter.ATTACKER).getOne(Integer.class);
		if(!game.actualState.containsIdentified(attackingID))
			throw new UnsupportedOperationException("Tried to attack ID " + attackingID + " which doesn't exist in the game state.");

		// 506.3c If an effect would put a creature onto the battlefield
		// attacking either a player not in the game or a planeswalker no
		// longer on the battlefield or no longer a planeswalker, that
		// creature does enter the battlefield, but it's never considered to
		// be an attacking creature.
		Identified attackingWho = game.actualState.get(attackingID);
		if(attackingWho.isGameObject())
		{
			GameObject planeswalker = (GameObject)attackingWho;
			if(!planeswalker.getTypes().contains(Type.PLANESWALKER) || planeswalker.isGhost())
				return false;
		}
		else if(((Player)attackingWho).outOfGame)
			return false;

		for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
		{
			GameObject physicalObject = object.getPhysical();
			physicalObject.setAttackingID(attackingID);
			physicalObject.setBlockedByIDs(null);
		}

		return true;
	}
};