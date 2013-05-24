package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the amount of life gained by each of the given players this
 * turn.
 */
public class LifeGainedThisTurn extends SetGenerator
{
	public static class LifeTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> lifeGained = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.lifeGained);

		@SuppressWarnings("unchecked")
		@Override
		public LifeTracker clone()
		{
			LifeTracker ret = (LifeTracker)super.clone();
			ret.lifeGained = (java.util.HashMap<Integer, Integer>)this.lifeGained.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.lifeGained);
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
			return event.type == EventType.GAIN_LIFE;
		}

		@Override
		protected void reset()
		{
			this.lifeGained.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Set players = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null);
			int loss = event.parametersNow.get(EventType.Parameter.NUMBER).evaluate(state, null).getOne(Integer.class);
			for(Player p: players.getAll(Player.class))
			{
				if(this.lifeGained.containsKey(p.ID))
					this.lifeGained.put(p.ID, this.lifeGained.get(p.ID) + loss);
				else
					this.lifeGained.put(p.ID, loss);
			}
		}
	}

	public static LifeGainedThisTurn instance(SetGenerator players)
	{
		return new LifeGainedThisTurn(players);
	}

	private final SetGenerator players;

	private LifeGainedThisTurn(SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Turn currentTurn = state.currentTurn();
		if(currentTurn == null)
			return Empty.set;

		Set ret = new Set();
		Set what = this.players.evaluate(state, thisObject);
		java.util.Map<Integer, Integer> value = state.getTracker(LifeTracker.class).getValue(state);

		for(Player p: what.getAll(Player.class))
		{
			Integer lifeGained = value.get(p.ID);
			if(lifeGained != null)
				ret.add(lifeGained);
		}
		return ret;
	}
}
