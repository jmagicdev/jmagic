package org.rnd.jmagic.engine.trackers;

import org.rnd.jmagic.engine.*;

/**
 * Keys are playerIDs, values are the number of cards they've drawn this turn
 */
public final class DrawTracker extends Tracker<java.util.Map<Integer, Integer>>
{
	private java.util.Map<Integer, Integer> value = new java.util.HashMap<Integer, Integer>();
	private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(value);

	@Override
	protected Tracker<java.util.Map<java.lang.Integer, java.lang.Integer>> clone()
	{
		DrawTracker ret = (DrawTracker)super.clone();
		ret.value = new java.util.HashMap<Integer, Integer>(this.value);
		ret.unmodifiable = java.util.Collections.unmodifiableMap(this.value);
		return ret;
	}

	@Override
	protected java.util.Map<Integer, Integer> getValueInternal()
	{
		return this.unmodifiable;
	}

	@Override
	protected void reset()
	{
		this.value.clear();
	}

	@Override
	protected boolean match(GameState state, Event event)
	{
		return event.type == EventType.DRAW_ONE_CARD;
	}

	@Override
	protected void update(GameState state, Event event)
	{
		int playerID = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;
		if(this.value.containsKey(playerID))
			this.value.put(playerID, this.value.get(playerID) + 1);
		else
			this.value.put(playerID, 1);
	}
}