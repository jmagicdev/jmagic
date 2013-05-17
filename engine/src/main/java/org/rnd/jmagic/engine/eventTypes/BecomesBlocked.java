package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BecomesBlocked extends EventType
{	public static final EventType INSTANCE = new BecomesBlocked();

	 private BecomesBlocked()
	{
		super("BECOMES_BLOCKED");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.ATTACKER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Set attackers = parameters.get(Parameter.ATTACKER);

		for(GameObject attacker: attackers.getAll(GameObject.class))
		{
			GameObject physicalAttacker = attacker.getPhysical();
			if(physicalAttacker.getBlockedByIDs() == null)
			{
				attacker.setBlockedByIDs(new java.util.LinkedList<Integer>());
				physicalAttacker.setBlockedByIDs(new java.util.LinkedList<Integer>());
			}
		}

		for(GameObject blocker: parameters.get(Parameter.DEFENDER).getAll(GameObject.class))
		{
			Set blockerSet = new Set(blocker);
			java.util.Map<Parameter, Set> blockedParameters = new java.util.HashMap<Parameter, Set>();
			blockedParameters.put(Parameter.ATTACKER, attackers);
			blockedParameters.put(Parameter.DEFENDER, blockerSet);
			createEvent(game, blockerSet + " blocks " + attackers, EventType.BECOMES_BLOCKED_BY_ONE, blockedParameters).perform(event, false);
		}

		event.setResult(Empty.set);
		return true;
	}
}