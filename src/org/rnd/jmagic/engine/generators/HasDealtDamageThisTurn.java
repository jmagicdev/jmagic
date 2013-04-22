package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class HasDealtDamageThisTurn extends SetGenerator
{
	private static HasDealtDamageThisTurn _instance = null;

	public static HasDealtDamageThisTurn instance()
	{
		if(_instance == null)
		{
			_instance = new HasDealtDamageThisTurn();
		}
		return _instance;
	}

	private HasDealtDamageThisTurn()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Tracker<java.util.Map<Integer, java.util.Set<Integer>>> flag = state.getTracker(DealtDamageByThisTurn.DealtDamageByTracker.class);

		java.util.Set<Integer> ids = new java.util.HashSet<Integer>(flag.getValue(state).keySet());

		Set ret = new Set();
		for(Integer id: ids)
			ret.add(state.get(id));

		return ret;
	}

}
