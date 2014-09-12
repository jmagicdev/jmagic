package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * All objects that have used up their loyalty activation(s) this turn.
 */
public class LoyaltyUsedUpThisTurn extends SetGenerator
{
	/**
	 * Keys are IDs of GameObjects, values are how many times that object has
	 * had a loyalty ability activated.
	 */
	public static class Counter extends Tracker<java.util.Map<Integer, Integer>>
	{
		private java.util.HashMap<Integer, Integer> values = new java.util.HashMap<>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		@SuppressWarnings("unchecked")
		@Override
		public Counter clone()
		{
			Counter ret = (Counter)super.clone();
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
			if(event.type == EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			{
				GameObject ability = event.getResult(state).getOne(GameObject.class);
				if(ability.isActivatedAbility())
					for(EventFactory cost: ability.getCosts())
						if(cost.type == EventType.REMOVE_COUNTERS || cost.type == EventType.PUT_COUNTERS)
						{
							java.util.Set<org.rnd.jmagic.engine.Counter.CounterType> counterTypes = cost.parameters.get(EventType.Parameter.COUNTER).evaluate(state, ability).getAll(org.rnd.jmagic.engine.Counter.CounterType.class);
							if(counterTypes.size() == 1 && counterTypes.contains(org.rnd.jmagic.engine.Counter.CounterType.LOYALTY))
								return true;
						}
			}

			return false;
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			int sourceID = ((ActivatedAbility)event.getResult(state).getOne(GameObject.class)).sourceID;
			if(this.values.containsKey(sourceID))
				this.values.put(sourceID, this.values.get(sourceID) + 1);
			else
				this.values.put(sourceID, 1);
		}
	}

	public static LoyaltyUsedUpThisTurn instance()
	{
		return new LoyaltyUsedUpThisTurn();
	}

	private LoyaltyUsedUpThisTurn()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Counter counter = state.getTracker(Counter.class);
		if(counter == null)
			return Empty.set;

		Set ret = new Set();
		for(java.util.Map.Entry<Integer, Integer> entry: counter.getValue(state).entrySet())
		{
			GameObject object = state.getByIDObject(entry.getKey());
			if(object == null) // if it's a ghost we don't care about it
				continue;
			if(object.maxLoyaltyActivations == entry.getValue())
				ret.add(object);
		}
		return ret;
	}
}
