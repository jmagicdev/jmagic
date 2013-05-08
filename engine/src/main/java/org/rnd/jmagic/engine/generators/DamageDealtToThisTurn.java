package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the total amount of damage taken by all given players and
 * objects this turn. This includes damage that was dealt to an object and then
 * removed (by, say, regeneration).
 */
public class DamageDealtToThisTurn extends SetGenerator
{
	/** Keys are Player IDs, values are amounts of damage that player has taken. */
	public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> values = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		@Override
		protected Tracker clone()
		{
			Tracker ret = (Tracker)super.clone();
			ret.values = new java.util.HashMap<Integer, Integer>(this.values);
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
			return event.type == EventType.DEAL_DAMAGE_BATCHES;
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			for(DamageAssignment damage: event.parameters.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(DamageAssignment.class))
			{
				if(this.values.containsKey(damage.takerID))
					this.values.put(damage.takerID, this.values.get(damage.takerID) + 1);
				else
					this.values.put(damage.takerID, 1);
			}
		}
	}

	public static DamageDealtToThisTurn instance(SetGenerator what)
	{
		return new DamageDealtToThisTurn(what);
	}

	private final SetGenerator what;

	private DamageDealtToThisTurn(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Turn currentTurn = state.currentTurn();
		if(currentTurn == null)
			return new Set(0);

		int total = 0;
		Set what = this.what.evaluate(state, thisObject);

		java.util.Map<Integer, Integer> flagValue = state.getTracker(Tracker.class).getValue(state);
		for(Identified i: what.getAll(Identified.class))
			if(flagValue.containsKey(i.ID))
				total += flagValue.get(i.ID);

		return new Set(total);
	}
}
