package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class UntilNextTurn extends SetGenerator
{
	public static abstract class EventAndBeginTurnTracker extends org.rnd.jmagic.engine.Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
	{
		private EventType eventType;
		private java.util.Map<Integer, java.util.Set<Integer>> values = new java.util.HashMap<Integer, java.util.Set<Integer>>();
		private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		protected EventAndBeginTurnTracker(EventType eventType)
		{
			this.eventType = eventType;
		}

		@Override
		protected EventAndBeginTurnTracker clone()
		{
			EventAndBeginTurnTracker ret = (EventAndBeginTurnTracker)super.clone();
			ret.values = new java.util.HashMap<Integer, java.util.Set<Integer>>();
			for(java.util.Map.Entry<Integer, java.util.Set<Integer>> entry: this.values.entrySet())
				ret.values.put(entry.getKey(), new java.util.HashSet<Integer>(entry.getValue()));
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.values);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, java.util.Set<Integer>> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == this.eventType || event.type == EventType.BEGIN_TURN;
		}

		@Override
		protected void reset()
		{
			// Do nothing, clearing is handled by update.
		}

		@Override
		protected void update(GameState state, Event event)
		{
			if(event.type == this.eventType)
			{
				Set cause = event.parametersNow.get(EventType.Parameter.CAUSE).evaluate(state, null);
				for(GameObject o: cause.getAll(GameObject.class))
				{
					Player controller = o.getController(state);
					if(!this.values.containsKey(controller.ID))
						this.values.put(controller.ID, new java.util.HashSet<Integer>());
					this.values.get(controller.ID).add(o.ID);
				}
			}
			else if(event.type == EventType.BEGIN_TURN)
			{
				Turn turn = event.parametersNow.get(EventType.Parameter.TURN).evaluate(state, null).getOne(Turn.class);
				Player player = turn.getOwner(state);
				if(this.values.containsKey(player.ID))
					this.values.remove(player.ID);
			}
		}
	}

	private Class<? extends EventAndBeginTurnTracker> trackerClass;

	public static UntilNextTurn instance(Class<? extends EventAndBeginTurnTracker> trackerClass)
	{
		return new UntilNextTurn(trackerClass);
	}

	private UntilNextTurn(Class<? extends EventAndBeginTurnTracker> trackerClass)
	{
		this.trackerClass = trackerClass;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		EventAndBeginTurnTracker tracker = state.getTracker(this.trackerClass);
		for(java.util.Map.Entry<Integer, java.util.Set<Integer>> entry: tracker.getValue(state).entrySet())
			for(Integer id: entry.getValue())
				ret.add(state.get(id));
		return ret;
	}
}
