package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the id of each defender for each given attacker
 */
public class AttackingID extends SetGenerator
{
	public static AttackingID instance(SetGenerator what)
	{
		return new AttackingID(what);
	}

	private final SetGenerator what;

	private AttackingID(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			ret.add(object.getAttackingID());
		return ret;
	}
}
