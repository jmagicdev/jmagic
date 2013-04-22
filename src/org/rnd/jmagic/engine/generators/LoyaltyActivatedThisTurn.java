package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If anything in the given set had one of its loyalty abilities activated this
 * turn, evaluates to non-empty.
 */
public class LoyaltyActivatedThisTurn extends SetGenerator
{
	public static class Counter extends Tracker<java.util.Set<Integer>>
	{
		private java.util.HashSet<Integer> values = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

		@SuppressWarnings("unchecked")
		@Override
		public Counter clone()
		{
			Counter ret = (Counter)super.clone();
			ret.values = (java.util.HashSet<Integer>)this.values.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type == EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			{
				GameObject ability = event.getResult(state).getOne(GameObject.class);
				if(!ability.isSpell())
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
			GameObject ability = event.getResult(state).getOne(GameObject.class);
			this.values.add(((ActivatedAbility)ability).sourceID);
		}
	}

	public static LoyaltyActivatedThisTurn instance()
	{
		return new LoyaltyActivatedThisTurn();
	}

	private LoyaltyActivatedThisTurn()
	{
		// Singleton constructor
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Counter counter = state.getTracker(Counter.class);
		if(counter == null)
			return Empty.set;
		return IdentifiedWithID.instance(counter.getValue(state)).evaluate(state, null);
	}
}
