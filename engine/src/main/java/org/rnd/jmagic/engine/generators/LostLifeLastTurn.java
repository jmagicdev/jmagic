package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public final class LostLifeLastTurn extends SetGenerator
{
	// values are IDs of players who lost life last turn
	public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Set<Integer>>
	{
		private java.util.Set<Integer> thisTurn = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> previousTurn = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.previousTurn);

		public Tracker clone()
		{
			Tracker ret = (Tracker)super.clone();
			ret.thisTurn = new java.util.HashSet<Integer>(this.thisTurn);
			ret.previousTurn = new java.util.HashSet<Integer>(this.previousTurn);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.previousTurn);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			// actively-resetting tracker
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.LOSE_LIFE_ONE_PLAYER || event.type == EventType.BEGIN_TURN;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			if(event.type == EventType.BEGIN_TURN)
			{
				this.previousTurn.clear();
				this.previousTurn.addAll(this.thisTurn);
				this.thisTurn.clear();
				return;
			}

			this.thisTurn.add(event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID);
		}
	}

	private static SetGenerator _instance = new LostLifeLastTurn();

	private LostLifeLastTurn()
	{
		// singleton
	}

	public static SetGenerator instance()
	{
		return _instance;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> flag = state.getTracker(Tracker.class).getValue(state);
		return flag.stream().map(i -> state.get(i)).collect(java.util.stream.Collectors.toCollection(Set::new));
	}
}