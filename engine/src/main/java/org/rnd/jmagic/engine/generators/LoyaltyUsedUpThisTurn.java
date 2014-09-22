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

	private final SetGenerator ofWhat;

	public static LoyaltyUsedUpThisTurn instance(SetGenerator ofWhat)
	{
		return new LoyaltyUsedUpThisTurn(ofWhat);
	}

	private LoyaltyUsedUpThisTurn(SetGenerator ofWhat)
	{
		this.ofWhat = ofWhat;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Map<Integer, Integer> flag = state.getTracker(Counter.class).getValue(state);
		for(GameObject o: this.ofWhat.evaluate(state, thisObject).getAll(GameObject.class))
		{
			if(!o.getTypes().contains(Type.PLANESWALKER))
			{
				ret.add(o);
				continue;
			}
			if(flag.containsKey(o.ID) && flag.get(o.ID) >= o.maxLoyaltyActivations)
				ret.add(o);
		}
		return ret;
	}
}
