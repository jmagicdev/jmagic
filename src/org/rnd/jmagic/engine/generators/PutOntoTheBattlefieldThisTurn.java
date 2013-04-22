package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Resolves to the set of all objects that were put onto the battlefield this
 * turn.
 */
public class PutOntoTheBattlefieldThisTurn extends SetGenerator
{
	public static class BirthTracker extends Tracker<java.util.Set<Integer>>
	{
		private java.util.HashSet<Integer> IDs = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.IDs);

		@SuppressWarnings("unchecked")
		@Override
		public BirthTracker clone()
		{
			BirthTracker ret = (BirthTracker)super.clone();
			ret.IDs = (java.util.HashSet<Integer>)this.IDs.clone();
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
				if(state.battlefield().ID == change.destinationZoneID)
					return true;
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
			Set objects = event.getResult(state);
			for(GameObject o: objects.getAll(GameObject.class))
				this.IDs.add(o.ID);
		}
	}

	private static final PutOntoTheBattlefieldThisTurn _instance = new PutOntoTheBattlefieldThisTurn();

	public static PutOntoTheBattlefieldThisTurn instance()
	{
		return _instance;
	}

	private PutOntoTheBattlefieldThisTurn()
	{
		// singleton
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Turn currentTurn = state.currentTurn();
		if(currentTurn == null)
			return Empty.set;

		Set ret = new Set();
		for(int ID: state.getTracker(BirthTracker.class).getValue(state))
			ret.add(state.get(ID));
		return ret;
	}
}
