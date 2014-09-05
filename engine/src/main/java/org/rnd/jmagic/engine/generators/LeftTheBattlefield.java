package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all objects that have left the battlefield this turn. You must
 * ensure the LeavesTheBattlefieldTracker to use this generator.
 */
public class LeftTheBattlefield extends SetGenerator
{
	public static class LeavesTheBattlefieldTracker extends Tracker<java.util.Set<Integer>>
	{
		private java.util.HashSet<Integer> IDs = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(IDs);

		@Override
		public LeavesTheBattlefieldTracker clone()
		{
			LeavesTheBattlefieldTracker ret = (LeavesTheBattlefieldTracker)super.clone();
			ret.IDs = new java.util.HashSet<Integer>();
			ret.IDs.addAll(this.IDs);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.IDs);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.MOVE_BATCH)
				return false;

			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(state.battlefield().ID != change.sourceZoneID)
					continue;

				return true;
			}

			return false;
		}

		@Override
		protected void reset()
		{
			this.IDs.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(state.battlefield().ID == change.sourceZoneID)
					this.IDs.add(change.oldObjectID);
			}
		}
	}

	public static SetGenerator instance()
	{
		return _instance;
	}

	private static SetGenerator _instance = new LeftTheBattlefield();

	private LeftTheBattlefield()
	{
		// singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(int ID: state.getTracker(LeavesTheBattlefieldTracker.class).getValue(state))
			ret.add(state.get(ID));
		return ret;
	}

}