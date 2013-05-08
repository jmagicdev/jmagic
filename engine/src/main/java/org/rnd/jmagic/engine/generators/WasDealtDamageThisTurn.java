package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class WasDealtDamageThisTurn extends SetGenerator
{
	private static WasDealtDamageThisTurn _instance = null;

	public static WasDealtDamageThisTurn instance()
	{
		if(_instance == null)
		{
			_instance = new WasDealtDamageThisTurn();
		}
		return _instance;
	}

	private WasDealtDamageThisTurn()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Tracker<java.util.Map<Integer, java.util.Set<Integer>>> flag = state.getTracker(DealtDamageByThisTurn.DealtDamageByTracker.class);

		java.util.Set<Integer> ids = new java.util.HashSet<Integer>();
		for(java.util.Set<Integer> damagers: flag.getValue(state).values())
			ids.addAll(damagers);

		Set ret = new Set();
		for(Integer id: ids)
			ret.add(state.get(id));

		return ret;
	}

}
