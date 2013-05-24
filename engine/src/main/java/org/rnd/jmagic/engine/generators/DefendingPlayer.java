package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the player being attacked or the controller of the planeswalker
 * being attacked by the given {@link GameObject}s
 */
public class DefendingPlayer extends SetGenerator
{
	public static DefendingPlayer instance(SetGenerator what)
	{
		return new DefendingPlayer(what);
	}

	private final SetGenerator what;

	private DefendingPlayer(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> ids = new java.util.HashSet<Integer>();

		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			if(object.getAttackingID() != -1)
				ids.add(object.getAttackingID());

		Set ret = new Set();
		for(Integer id: ids)
		{
			Identified defender = state.get(id);
			if(defender.isGameObject() && defender.attackable())
				ret.add(((GameObject)defender).getController(state));
			else if(defender.isPlayer())
				ret.add(defender);
			else
				throw new RuntimeException("Creature attacking non-Planeswalker, non-Player.");
		}

		return ret;
	}

}
