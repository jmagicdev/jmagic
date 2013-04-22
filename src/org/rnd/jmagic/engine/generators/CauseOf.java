package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the cause of any ZoneChange in the inner SetGenerator.
 */
public class CauseOf extends SetGenerator
{
	public static CauseOf instance(SetGenerator zoneChanges)
	{
		return new CauseOf(zoneChanges);
	}

	private final SetGenerator zoneChanges;

	private CauseOf(SetGenerator zoneChanges)
	{
		this.zoneChanges = zoneChanges;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ZoneChange change: this.zoneChanges.evaluate(state, thisObject).getAll(ZoneChange.class))
		{
			if(0 == change.causeID)
				ret.add(state.game);
			else
				ret.add(state.get(change.causeID));
		}
		return ret;
	}
}
