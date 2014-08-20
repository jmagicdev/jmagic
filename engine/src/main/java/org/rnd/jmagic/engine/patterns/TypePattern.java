package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Matches against objects that have any one of the given {@link Type}s.
 */
public class TypePattern implements SetPattern
{
	private SetGenerator types;

	public TypePattern(Type... types)
	{
		this.types = Identity.instance((Object[])types);
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		this.types = Identity.fromCollection(this.types.evaluate(state, thisObject)).noTextChanges();
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set types = this.types.evaluate(state, thisObject);
		for(GameObject object: set.getAll(GameObject.class))
			for(Type t: object.getTypes())
				if(types.contains(t))
					return true;

		return false;
	}
}
