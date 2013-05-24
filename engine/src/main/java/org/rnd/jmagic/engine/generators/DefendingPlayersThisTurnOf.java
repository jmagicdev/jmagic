package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Resolves to the set of all non-ghost objects that attacked this turn.
 */
public class DefendingPlayersThisTurnOf extends SetGenerator
{
	public static DefendingPlayersThisTurnOf instance(SetGenerator what)
	{
		return new DefendingPlayersThisTurnOf(what);
	}

	private SetGenerator what;

	private DefendingPlayersThisTurnOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		java.util.Map<Integer, java.util.Set<Integer>> tracker = state.getTracker(SuccessfullyAttacked.class).getValue(state);
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			if(tracker.containsKey(object.ID))
				for(Integer id: tracker.get(object.ID))
				{
					Identified defender = state.get(id);
					if(defender.isGameObject() && defender.attackable())
						ret.add(((GameObject)defender).getController(state));
					else if(defender.isPlayer())
						ret.add(defender);
				}

		return ret;
	}
}
