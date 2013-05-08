package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public final class OwnedByPattern implements SetPattern
{
	private SetGenerator owners;

	public OwnedByPattern(SetGenerator owners)
	{
		this.owners = owners;
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set owners = this.owners.evaluate(state, thisObject);
		for(GameObject object: set.getAll(GameObject.class))
			if(owners.contains(object.getOwner(state)))
				return true;
		return false;
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// nothing to freeze
	}
}
