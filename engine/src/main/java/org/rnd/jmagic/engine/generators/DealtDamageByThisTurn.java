package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all objects and players that were dealt damage by any of the
 * given objects this turn.
 */
public class DealtDamageByThisTurn extends SetGenerator
{
	public static class DealtDamageByTracker extends Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
	{
		private java.util.HashMap<Integer, java.util.Set<Integer>> ids = new java.util.HashMap<Integer, java.util.Set<Integer>>();
		private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.ids);

		@SuppressWarnings("unchecked")
		@Override
		public DealtDamageByTracker clone()
		{
			DealtDamageByTracker ret = (DealtDamageByTracker)super.clone();
			ret.ids = (java.util.HashMap<Integer, java.util.Set<Integer>>)this.ids.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.ids);
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
			return event.type == EventType.DEAL_DAMAGE_BATCHES;
		}

		@Override
		protected void reset()
		{
			this.ids.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			java.util.Set<DamageAssignment> assignments = event.parameters.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(DamageAssignment.class);
			for(DamageAssignment assignment: assignments)
			{
				if(!this.ids.containsKey(assignment.sourceID))
					this.ids.put(assignment.sourceID, new java.util.HashSet<Integer>());
				this.ids.get(assignment.sourceID).add(assignment.takerID);
			}
		}

	}

	public static class DealtCombatDamageByTracker extends Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
	{
		private java.util.HashMap<Integer, java.util.Set<Integer>> ids = new java.util.HashMap<Integer, java.util.Set<Integer>>();
		private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.ids);

		@SuppressWarnings("unchecked")
		@Override
		public DealtCombatDamageByTracker clone()
		{
			DealtCombatDamageByTracker ret = (DealtCombatDamageByTracker)super.clone();
			ret.ids = (java.util.HashMap<Integer, java.util.Set<Integer>>)this.ids.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.ids);
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
			return event.type == EventType.DEAL_DAMAGE_BATCHES;
		}

		@Override
		protected void reset()
		{
			this.ids.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			java.util.Set<DamageAssignment> assignments = event.parameters.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(DamageAssignment.class);
			for(DamageAssignment assignment: assignments)
			{
				if(!assignment.isCombatDamage)
					continue;
				if(!this.ids.containsKey(assignment.sourceID))
					this.ids.put(assignment.sourceID, new java.util.HashSet<Integer>());
				this.ids.get(assignment.sourceID).add(assignment.takerID);
			}
		}

	}

	public static DealtDamageByThisTurn instance(SetGenerator what)
	{
		return new DealtDamageByThisTurn(what, false);
	}

	public static DealtDamageByThisTurn instance(SetGenerator what, boolean combatDamage)
	{
		return new DealtDamageByThisTurn(what, combatDamage);
	}

	private final SetGenerator what;
	private final boolean combatDamage;

	private DealtDamageByThisTurn(SetGenerator what, boolean combatDamage)
	{
		this.what = what;
		this.combatDamage = combatDamage;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Tracker<java.util.Map<Integer, java.util.Set<Integer>>> flag = null;
		if(this.combatDamage)
			flag = state.getTracker(DealtCombatDamageByTracker.class);
		else
			flag = state.getTracker(DealtDamageByTracker.class);

		java.util.Map<Integer, java.util.Set<Integer>> flagValue = flag.getValue(state);

		java.util.Set<Integer> ids = new java.util.HashSet<Integer>();
		Set what = this.what.evaluate(state, thisObject);
		for(GameObject dealer: what.getAll(GameObject.class))
			if(flagValue.containsKey(dealer.ID))
			{
				java.util.Set<Integer> dealtDamageByThis = flagValue.get(dealer.ID);
				ids.addAll(dealtDamageByThis);
			}
		return IdentifiedWithID.instance(ids).evaluate(state, null);
	}
}
