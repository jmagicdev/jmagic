package org.rnd.jmagic.engine.trackers;

import org.rnd.jmagic.engine.*;

/**
 * A tracker that maps each player to the spells they have cast this turn. If a
 * player is not in the map, they have not cast any spells.
 */
public final class CastTracker extends Tracker<java.util.Map<Integer, java.util.List<Integer>>>
{
	private java.util.Map<Integer, java.util.List<Integer>> map = new java.util.HashMap<Integer, java.util.List<Integer>>();
	private java.util.Map<Integer, java.util.List<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.map);

	@Override
	public CastTracker clone()
	{
		CastTracker ret = (CastTracker)super.clone();
		ret.map = new java.util.HashMap<Integer, java.util.List<Integer>>(this.map);
		ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.map);
		return ret;
	}

	@Override
	protected java.util.Map<Integer, java.util.List<Integer>> getValueInternal()
	{
		return this.unmodifiable;
	}

	@Override
	protected void reset()
	{
		// Never resets
	}

	@Override
	protected boolean match(GameState state, Event event)
	{
		return event.type == EventType.BECOMES_PLAYED && event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class).isSpell();
	}

	@Override
	protected void update(GameState state, Event event)
	{
		int player = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;
		if(!this.map.containsKey(player))
			this.map.put(player, new java.util.LinkedList<Integer>());

		GameObject spell = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
		this.map.get(player).add(spell.ID);
	}
}