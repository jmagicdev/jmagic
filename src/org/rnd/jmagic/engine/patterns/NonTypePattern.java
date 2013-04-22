package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Matches against objects that none of the given {@link Type}s.
 */
public class NonTypePattern implements SetPattern
{
	private SetGenerator types;

	public NonTypePattern(Type... types)
	{
		this.types = Identity.instance((Object[])types);
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		this.types = Identity.instance(this.types.evaluate(state, thisObject)).noTextChanges();
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set types = this.types.evaluate(state, thisObject);
		for(GameObject object: set.getAll(GameObject.class))
			for(Type t: object.getTypes())
				if(types.contains(t))
					return false;

		return true;
	}
}
