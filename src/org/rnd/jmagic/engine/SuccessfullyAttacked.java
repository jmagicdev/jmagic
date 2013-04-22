package org.rnd.jmagic.engine;

/**
 * Tracks what {@link Player}/Planeswalkers a {@link GameObject} has
 * successfully attacked this turn. The value is a map from the
 * {@link GameObject}'s ID to a {@link java.util.Set} of IDs of {@link Player}s
 * or Planeswalkers successfully attacked this turn.
 */
public class SuccessfullyAttacked extends Tracker<java.util.Map<Integer, java.util.Set<Integer>>>
{
	private java.util.Map<Integer, java.util.Set<Integer>> values = new java.util.HashMap<Integer, java.util.Set<Integer>>();
	private java.util.Map<Integer, java.util.Set<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

	@Override
	public SuccessfullyAttacked clone()
	{
		SuccessfullyAttacked ret = (SuccessfullyAttacked)super.clone();
		ret.values = new java.util.HashMap<Integer, java.util.Set<Integer>>();
		for(java.util.Map.Entry<Integer, java.util.Set<Integer>> e: this.values.entrySet())
			ret.values.put(e.getKey(), new java.util.HashSet<Integer>(e.getValue()));
		ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.values);
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
		return EventType.DECLARE_ONE_ATTACKER == event.type;
	}

	@Override
	protected void reset()
	{
		this.values.clear();
	}

	@Override
	protected void update(GameState state, Event event)
	{
		java.util.Set<Identified> defenders = event.parametersNow.get(EventType.Parameter.DEFENDER).evaluate(state, null).getAll(Identified.class);
		for(GameObject attacker: event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).getAll(GameObject.class))
		{
			java.util.Set<Integer> attacked;
			if(this.values.containsKey(attacker.ID))
				attacked = this.values.get(attacker.ID);
			else
			{
				attacked = new java.util.HashSet<Integer>();
				this.values.put(attacker.ID, attacked);
			}

			for(Identified i: defenders)
				attacked.add(i.ID);
		}
	}
}
