package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

public class LandsPutOntoTheBattlefieldThisTurnCounter extends MaximumPerPlayer.GameObjectsThisTurnCounter
{
	@Override
	protected boolean match(GameState state, Event event)
	{
		if(event.type != EventType.MOVE_BATCH)
			return false;

		for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
		{
			if(state.battlefield().ID != change.destinationZoneID)
				continue;

			GameObject object = state.get(change.newObjectID);
			if(!object.getTypes().contains(Type.LAND))
				continue;

			return true;
		}
		return false;
	}
}