package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Matches against objects that have any one of the given {@link SubType}s.
 */
public class SubTypePattern implements SetPattern
{
	private SetGenerator subtypes;

	public SubTypePattern(SubType... types)
	{
		this.subtypes = Identity.instance((Object[])types);
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		this.subtypes = Identity.instance(this.subtypes.evaluate(state, thisObject)).noTextChanges();
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set subtypes = this.subtypes.evaluate(state, thisObject);
		for(GameObject object: set.getAll(GameObject.class))
			for(SubType s: object.getSubTypes())
				if(subtypes.contains(s))
					return true;

		return false;
	}
}
