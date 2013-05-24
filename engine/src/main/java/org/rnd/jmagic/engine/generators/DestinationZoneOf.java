package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the destination zone of any ZoneChange
 */
public class DestinationZoneOf extends SetGenerator
{
	public static DestinationZoneOf instance(SetGenerator zoneChanges)
	{
		return new DestinationZoneOf(zoneChanges);
	}

	private final SetGenerator zoneChanges;

	private DestinationZoneOf(SetGenerator zoneChanges)
	{
		this.zoneChanges = zoneChanges;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ZoneChange change: this.zoneChanges.evaluate(state, thisObject).getAll(ZoneChange.class))
			ret.add(state.get(change.destinationZoneID));
		return ret;
	}

}
