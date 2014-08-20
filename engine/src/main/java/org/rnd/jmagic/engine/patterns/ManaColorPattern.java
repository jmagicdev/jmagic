package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Matches against mana symbols that have any one of the given {@link Color}s.
 */
public class ManaColorPattern implements SetPattern
{
	private SetGenerator colors;

	public ManaColorPattern(Color... types)
	{
		this.colors = Identity.instance((Object[])types);
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		this.colors = Identity.fromCollection(this.colors.evaluate(state, thisObject)).noTextChanges();
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set colors = this.colors.evaluate(state, thisObject);
		for(ManaSymbol m: set.getAll(ManaSymbol.class))
			for(Color c: m.colors)
				if(colors.contains(c))
					return true;

		return false;
	}
}
