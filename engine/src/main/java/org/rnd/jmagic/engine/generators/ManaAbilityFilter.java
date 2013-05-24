package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all mana abilities of the given set
 */
public class ManaAbilityFilter extends SetGenerator
{
	public static ManaAbilityFilter instance(SetGenerator what)
	{
		return new ManaAbilityFilter(what);
	}

	private final SetGenerator what;

	private ManaAbilityFilter(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(NonStaticAbility ability: this.what.evaluate(state, thisObject).getAll(NonStaticAbility.class))
			if(ability.isManaAbility())
				ret.add(ability);
		return ret;
	}

}
