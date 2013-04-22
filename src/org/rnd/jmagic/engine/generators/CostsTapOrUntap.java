package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all activated abilities that have {T} or {Q} in their costs from
 * the given set.
 */
public class CostsTapOrUntap extends SetGenerator
{
	public static CostsTapOrUntap instance(SetGenerator what)
	{
		return new CostsTapOrUntap(what);
	}

	private final SetGenerator what;

	private CostsTapOrUntap(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ActivatedAbility ability: this.what.evaluate(state, thisObject).getAll(ActivatedAbility.class))
			if(ability.costsTap || ability.costsUntap)
				ret.add(ability);
		return ret;
	}
}
