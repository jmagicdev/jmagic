package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class DestroyedThisTurn extends SetGenerator
{
	public static class DestroyedTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> IDs = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.IDs);

		@SuppressWarnings("unchecked")
		@Override
		public DestroyedTracker clone()
		{
			DestroyedTracker ret = (DestroyedTracker)super.clone();
			ret.IDs = (java.util.HashMap<Integer, Integer>)this.IDs.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.IDs);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return (event.type == EventType.DESTROY_ONE_PERMANENT);
		}

		@Override
		protected void reset()
		{
			this.IDs.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Set object = OldObjectOf.instance(event.getResultGenerator()).evaluate(state, null);
			Identified cause = event.parameters.get(EventType.Parameter.CAUSE).evaluate(state, event.getSource()).getOne(Identified.class);

			// If the cause is the game, use 0.
			int causeID = (cause == null ? 0 : cause.ID);

			for(GameObject o: object.getAll(GameObject.class))
				this.IDs.put(o.ID, causeID);
		}
	}

	private static DestroyedThisTurn _instance = new DestroyedThisTurn();

	public static DestroyedThisTurn instance()
	{
		return _instance;
	}

	private DestroyedThisTurn()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> ids = state.getTracker(DestroyedTracker.class).getValue(state).keySet();
		Set ret = new Set();

		for(Integer id: ids)
			ret.add(state.get(id));

		return ret;
	}
}
