package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public final class ControlledByPattern implements SetPattern
{
	private SetGenerator controllers;

	public ControlledByPattern(SetGenerator controllers)
	{
		this.controllers = controllers;
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set controllers = this.controllers.evaluate(state, thisObject);
		for(GameObject object: set.getAll(GameObject.class))
			if(controllers.contains(object.getController(state)))
				return true;
		return false;
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// nothing to freeze
	}
}
