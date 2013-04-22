package org.rnd.jmagic.engine;

/**
 * A flag whose purpose is to keep track of a count. The count starts at zero
 * each turn, and will increment every time <code>register</code> is called.
 * 
 * Because <code>match</code> always returns true, this is a Tracker that is not
 * intended to be added to a game state; rather it is intended to be used
 * internally by abilities which need to track something, and where what is
 * tracked is tracked separately for each instance of that ability.
 */
public class GenericIntegerTracker extends Tracker<Integer>
{
	private int count;

	@Override
	public GenericIntegerTracker clone()
	{
		GenericIntegerTracker ret = (GenericIntegerTracker)super.clone();
		ret.count = this.count;
		return ret;
	}

	@Override
	protected Integer getValueInternal()
	{
		return this.count;
	}

	@Override
	protected boolean match(GameState state, Event event)
	{
		return true;
	}

	@Override
	protected void reset()
	{
		this.count = 0;
	}

	@Override
	protected void update(GameState state, Event event)
	{
		++this.count;
	}
}