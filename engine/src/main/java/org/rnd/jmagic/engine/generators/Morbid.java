package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Morbid extends SetGenerator
{
	public static class Tracker extends PutIntoGraveyardsFromBattlefieldThisTurn.DeathTracker
	{
		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.MOVE_BATCH)
				return false;

			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(state.battlefield().ID != change.sourceZoneID)
					continue;

				Zone to = state.get(change.destinationZoneID);
				if(!to.isGraveyard())
					continue;

				if(!state.<GameObject>get(change.oldObjectID).getTypes().contains(Type.CREATURE))
					return false;

				return true;
			}

			return false;
		}
	}

	private static SetGenerator _instance = null;

	private Morbid()
	{
		// singleton generator
	}

	public static final SetGenerator instance()
	{
		if(_instance == null)
			_instance = new Morbid();
		return _instance;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.getTracker(Tracker.class).getValue(state).isEmpty())
			return Empty.set;
		return NonEmpty.set;
	}
}
