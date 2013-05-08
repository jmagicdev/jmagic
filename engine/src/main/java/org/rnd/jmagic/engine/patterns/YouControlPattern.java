package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public final class YouControlPattern implements SetPattern
{
	private SetPattern kind;

	public YouControlPattern(SetPattern kind)
	{
		this.kind = kind;
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		for(GameObject o: set.getAll(GameObject.class))
		{
			if(!this.kind.match(state, thisObject, set))
				continue;
			if(o.controllerID == ((GameObject)thisObject).controllerID)
				return true;
		}
		return false;
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// nothing to do here
	}
}