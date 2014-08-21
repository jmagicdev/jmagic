package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class BecomesBlockedByOne extends EventType
{
	public static final EventType INSTANCE = new BecomesBlockedByOne();

	private BecomesBlockedByOne()
	{
		super("BECOMES_BLOCKED_BY_ONE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.ATTACKER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		int blockerID = parameters.get(Parameter.DEFENDER).getOne(GameObject.class).ID;
		for(GameObject attacker: parameters.get(Parameter.ATTACKER).getAll(GameObject.class))
		{
			GameObject physicalAttacker = attacker.getPhysical();
			if(!physicalAttacker.getBlockedByIDs().contains(blockerID))
			{
				attacker.getBlockedByIDs().add(blockerID);
				physicalAttacker.getBlockedByIDs().add(blockerID);
			}
		}
		event.setResult(Empty.set);
		return true;
	}

}