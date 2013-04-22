package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public final class NumberCardsDrawnBy extends SetGenerator
{
	public static final class DrawCounter extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> values = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		@SuppressWarnings("unchecked")
		@Override
		public NumberCardsDrawnBy.DrawCounter clone()
		{
			NumberCardsDrawnBy.DrawCounter ret = (NumberCardsDrawnBy.DrawCounter)super.clone();
			ret.values = (java.util.HashMap<Integer, Integer>)this.values.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.values);
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
			return (event.type == EventType.DRAW_ONE_CARD);
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			int playerID = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;

			if(!this.values.containsKey(playerID))
				this.values.put(playerID, 1);
			else
				this.values.put(playerID, this.values.get(playerID) + 1);
		}
	}

	public static NumberCardsDrawnBy instance(SetGenerator what)
	{
		return new NumberCardsDrawnBy(what);
	}

	private SetGenerator what;

	private NumberCardsDrawnBy(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Map<Integer, Integer> flagValues = state.getTracker(NumberCardsDrawnBy.DrawCounter.class).getValue(state);
		for(Player player: this.what.evaluate(state, thisObject).getAll(Player.class))
		{
			Integer numDrawn = flagValues.get(player.ID);
			if(numDrawn == null)
				numDrawn = 0;
			ret.add(numDrawn);
		}
		return ret;
	}
}