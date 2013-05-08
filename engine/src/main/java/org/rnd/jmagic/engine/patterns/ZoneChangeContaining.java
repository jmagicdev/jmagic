package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class ZoneChangeContaining extends SimpleSetPattern
{
	public ZoneChangeContaining(SetGenerator pattern)
	{
		super(pattern);
	}

	@Override
	public boolean match(GameState state, Identified object, Set set)
	{
		Set newSet = new Set(set);
		for(ZoneChange change: set.getAll(ZoneChange.class))
		{
			newSet.add(state.get(change.oldObjectID));
			// If we're evaluating this pattern to determine whether a look-back
			// trigger should trigger, the new object might not actually exist
			// yet.
			if(state.containsIdentified(change.newObjectID))
				newSet.add(state.get(change.newObjectID));
			newSet.remove(change);
		}
		return super.match(state, object, newSet);
	}
}
