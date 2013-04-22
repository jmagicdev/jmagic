package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all ZoneChanges passed in that were caused by a discard
 */
public class Discarded extends SetGenerator
{
	public static Discarded instance(SetGenerator zoneChanges)
	{
		return new Discarded(zoneChanges);
	}

	private final SetGenerator zoneChanges;

	private Discarded(SetGenerator zoneChanges)
	{
		this.zoneChanges = zoneChanges;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ZoneChange change: this.zoneChanges.evaluate(state, thisObject).getAll(ZoneChange.class))
			if(change.isDiscard)
				ret.add(change);
		return ret;
	}
}
