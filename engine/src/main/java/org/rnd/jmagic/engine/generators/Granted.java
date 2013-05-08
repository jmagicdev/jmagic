package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the objects that granted the given abilities. Currently only
 * works for NonStaticAbilities.
 */
public class Granted extends SetGenerator
{
	private final SetGenerator what;

	public static SetGenerator instance(SetGenerator what)
	{
		return new Granted(what);
	}

	private Granted(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(NonStaticAbility a: this.what.evaluate(state, thisObject).getAll(NonStaticAbility.class))
			if(a.grantedByID != -1)
				ret.add(state.get(a.grantedByID));

		return ret;
	}
}
