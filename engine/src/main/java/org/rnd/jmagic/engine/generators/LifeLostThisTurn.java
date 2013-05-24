package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the amount of life lost by each of the given players this turn.
 */
public class LifeLostThisTurn extends SetGenerator
{
	public static class PlayersWhoLostLife extends SetGenerator
	{
		private static PlayersWhoLostLife _instance = new PlayersWhoLostLife();

		public static PlayersWhoLostLife instance()
		{
			return _instance;
		}

		private PlayersWhoLostLife()
		{
			// singleton generator
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Turn currentTurn = state.currentTurn();
			if(currentTurn == null)
				return Empty.set;

			Set ret = new Set();
			java.util.Map<Integer, Integer> value = state.getTracker(LifeTracker.class).getValue(state);
			for(Integer id: value.keySet())
			{
				Identified identified = state.get(id);
				if(identified.isPlayer())
					ret.add(identified);
			}

			return ret;
		}
	}

	public static class LifeTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> lifeLost = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.lifeLost);

		@SuppressWarnings("unchecked")
		@Override
		public LifeTracker clone()
		{
			LifeTracker ret = (LifeTracker)super.clone();
			ret.lifeLost = (java.util.HashMap<Integer, Integer>)this.lifeLost.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.lifeLost);
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
			return event.type == EventType.LOSE_LIFE;
		}

		@Override
		protected void reset()
		{
			this.lifeLost.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Set players = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null);
			int loss = event.parametersNow.get(EventType.Parameter.NUMBER).evaluate(state, null).getOne(Integer.class);
			for(Player p: players.getAll(Player.class))
			{
				if(this.lifeLost.containsKey(p.ID))
					this.lifeLost.put(p.ID, this.lifeLost.get(p.ID) + loss);
				else
					this.lifeLost.put(p.ID, loss);
			}
		}
	}

	public static LifeLostThisTurn instance(SetGenerator players)
	{
		return new LifeLostThisTurn(players);
	}

	private final SetGenerator players;

	private LifeLostThisTurn(SetGenerator players)
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
			Integer lifeLost = value.get(p.ID);
			if(lifeLost != null)
				ret.add(lifeLost);
		}
		return ret;
	}
}
