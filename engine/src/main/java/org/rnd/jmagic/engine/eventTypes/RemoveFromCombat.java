package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RemoveFromCombat extends EventType
{	public static final EventType INSTANCE = new RemoveFromCombat();

	 private RemoveFromCombat()
	{
		super("REMOVE_FROM_COMBAT");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set results = new Set();

		Set objectsAndPlayers = parameters.get(Parameter.OBJECT);
		for(GameObject o: objectsAndPlayers.getAll(GameObject.class))
		{
			if(o.isGhost())
				continue;

			GameObject physical = o.getPhysical();
			physical.setAttackingID(-1);
			physical.setBlockedByIDs(null);
			physical.setBlockingIDs(new java.util.LinkedList<Integer>());
			physical.setDefendingIDs(new java.util.HashSet<Integer>());
			results.add(physical);
		}

		for(Player p: objectsAndPlayers.getAll(Player.class))
		{
			Player physical = p.getPhysical();
			physical.defendingIDs.clear();
			results.add(physical);
		}
		event.setResult(Identity.fromCollection(results));
		return true;
	}
}