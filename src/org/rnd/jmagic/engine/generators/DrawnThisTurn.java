package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public final class DrawnThisTurn extends SetGenerator
{
	public static final class DrawTracker extends Tracker<java.util.Collection<ZoneChange>>
	{
		private java.util.HashSet<ZoneChange> values = new java.util.HashSet<ZoneChange>();
		private java.util.Collection<ZoneChange> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

		@Override
		public DrawnThisTurn.DrawTracker clone()
		{
			DrawnThisTurn.DrawTracker ret = (DrawnThisTurn.DrawTracker)super.clone();
			ret.values = new java.util.HashSet<ZoneChange>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
			return ret;
		}

		@Override
		protected java.util.Collection<ZoneChange> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return (event.type == EventType.DRAW_ONE_CARD);
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			ZoneChange draw = event.getResult().getOne(ZoneChange.class);
			this.values.add(draw);
		}
	}

	private static SetGenerator _instance;

	private DrawnThisTurn()
	{
		// singleton generator
	}

	public static SetGenerator instance()
	{
		if(_instance == null)
			_instance = new DrawnThisTurn();
		return _instance;
	}

	@Override
	public org.rnd.jmagic.engine.Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ZoneChange z: state.getTracker(DrawnThisTurn.DrawTracker.class).getValue(state))
			ret.add(state.get(z.newObjectID));
		return ret;
	}
}