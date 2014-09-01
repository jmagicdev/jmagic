package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If this object is monstrous, evaluates to this object. Empty if it's not
 * monstrous. (This will return ghosts if appropriate.)
 */
public class ThisIsMonstrous extends SetGenerator
{
	private static final ThisIsMonstrous _instance = new ThisIsMonstrous();

	public static ThisIsMonstrous instance()
	{
		return _instance;
	}

	private ThisIsMonstrous()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Tracker<java.util.Set<Integer>> tracker = state.getTracker(org.rnd.jmagic.engine.eventTypes.Monstrosity.MonstrousTracker.class);
		if(tracker.getValue(state).contains(thisObject.ID))
			return new Set(state.get(thisObject.ID));
		return Empty.set;
	}
}
