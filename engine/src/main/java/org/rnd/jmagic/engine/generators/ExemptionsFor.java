package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Lets an ability implement exemptions to its rules. For instance, Hexproof
 * uses this generator to fetch the objects that can target its source as though
 * it didn't have Hexproof, and removes them from the restrictions it creates.
 */
public class ExemptionsFor extends SetGenerator
{
	public static ExemptionsFor instance(SetGenerator what, Class<?> from)
	{
		return new ExemptionsFor(what, from);
	}

	private SetGenerator what;
	private Class<?> from;

	private ExemptionsFor(SetGenerator what, Class<?> from)
	{
		this.what = what;
		this.from = from;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		if(state.abilityExemptions.containsKey(this.from.getName()))
		{
			java.util.Map<Integer, Set> exemptions = state.abilityExemptions.get(this.from.getName());
			for(Identified object: this.what.evaluate(state, thisObject).getAll(Identified.class))
				if(exemptions.containsKey(object.ID))
					ret.addAll(exemptions.get(object.ID));
		}
		return ret;
	}
}
