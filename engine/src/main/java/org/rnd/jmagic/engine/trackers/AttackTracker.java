package org.rnd.jmagic.engine.trackers;

import org.rnd.jmagic.engine.*;

/**
 * IDs of each player who attacked with a creature this turn.
 */
public final class AttackTracker extends Tracker<java.util.Set<Integer>>
{
	private java.util.Set<Integer> values = new java.util.HashSet<Integer>();
	private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

	@Override
	protected AttackTracker clone()
	{
		AttackTracker ret = (AttackTracker)super.clone();
		ret.values = new java.util.HashSet<Integer>(this.values);
		ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
		return ret;
	}

	@Override
	protected java.util.Set<Integer> getValueInternal()
	{
		return this.unmodifiable;
	}

	@Override
	protected void reset()
	{
		this.values.clear();
	}

	@Override
	protected boolean match(GameState state, Event event)
	{
		return event.type == EventType.DECLARE_ONE_ATTACKER;
	}

	@Override
	protected void update(GameState state, Event event)
	{
		GameObject attacker = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
		this.values.add(attacker.controllerID);
	}
}