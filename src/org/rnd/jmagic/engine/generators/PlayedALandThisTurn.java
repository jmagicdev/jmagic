package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If at least one of the given players played a land this turn, evaluates to a
 * non empty set. If none of the given players played a land this turn,
 * evaluates to the empty set.
 */
public class PlayedALandThisTurn extends SetGenerator
{
	public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Collection<Integer>>
	{
		private java.util.LinkedList<Integer> values = new java.util.LinkedList<Integer>();
		private java.util.Collection<Integer> unmodifiable = java.util.Collections.unmodifiableCollection(this.values);

		@SuppressWarnings("unchecked")
		@Override
		public Tracker clone()
		{
			Tracker ret = (Tracker)super.clone();
			ret.values = (java.util.LinkedList<Integer>)this.values.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableCollection(ret.values);
			return ret;
		}

		@Override
		protected java.util.Collection<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.PLAY_LAND;
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			this.values.add(event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID);
		}
	}

	private SetGenerator who;

	private PlayedALandThisTurn(SetGenerator players)
	{
		this.who = players;
	}

	public static PlayedALandThisTurn instance(SetGenerator players)
	{
		return new PlayedALandThisTurn(players);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Turn currentTurn = state.currentTurn();
		if(currentTurn == null)
			return Empty.set;

		java.util.Collection<Integer> flagValue = state.getTracker(Tracker.class).getValue(state);
		for(Player p: this.who.evaluate(state, thisObject).getAll(Player.class))
			if(flagValue.contains(p.ID))
				return NonEmpty.set;
		return Empty.set;
	}
}
