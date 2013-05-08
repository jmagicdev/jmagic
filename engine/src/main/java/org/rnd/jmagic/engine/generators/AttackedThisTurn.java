package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Resolves to the set of all non-ghost objects that attacked this turn.
 */
public class AttackedThisTurn extends SetGenerator
{
	private static final AttackedThisTurn _instance = new AttackedThisTurn();

	public static AttackedThisTurn instance()
	{
		return _instance;
	}

	private AttackedThisTurn()
	{
		// singleton
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Turn currentTurn = state.currentTurn();
		if(null == currentTurn)
			return Empty.set;

		Set ret = new Set();
		for(Integer i: state.getTracker(SuccessfullyAttacked.class).getValue(state).keySet())
			ret.add(state.get(i));
		return ret;
	}
}
