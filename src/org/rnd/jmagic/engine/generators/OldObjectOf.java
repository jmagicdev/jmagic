package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the new object in any ZoneChange
 */
public class OldObjectOf extends SetGenerator
{
	public static OldObjectOf instance(SetGenerator zoneChanges)
	{
		return new OldObjectOf(zoneChanges);
	}

	private final SetGenerator zoneChanges;

	private OldObjectOf(SetGenerator zoneChanges)
	{
		this.zoneChanges = zoneChanges;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ZoneChange change: this.zoneChanges.evaluate(state, thisObject).getAll(ZoneChange.class))
			ret.add(state.get(change.oldObjectID));
		return ret;
	}

}
