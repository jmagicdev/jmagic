package org.rnd.jmagic.engine;

public abstract class Tracker<T> implements Cloneable
{
	public static class TrackerNotFoundException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public TrackerNotFoundException(Class<? extends Tracker<?>> trackerClass)
		{
			super("Tracker not found: " + trackerClass.getName());
		}
	}

	private Turn lastTurn;

	protected Tracker()
	{
		this.lastTurn = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Tracker<T> clone()
	{
		try
		{
			return (Tracker<T>)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}

	/**
	 * This equals implementation is meant to support a "singleton per Set"
	 * design.
	 */
	@Override
	public final boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		return getClass() == obj.getClass();
	}

	@Override
	public final int hashCode()
	{
		return /*31 + */this.getClass().hashCode();
	}

	public final T getValue(GameState state)
	{
		if(this.lastTurn != state.currentTurn())
		{
			this.lastTurn = state.currentTurn();
			this.reset();
		}

		return this.getValueInternal();
	}

	public final void register(GameState state, Event event)
	{
		if(this.match(state, event))
		{
			if(this.lastTurn != state.currentTurn())
			{
				this.lastTurn = state.currentTurn();
				this.reset();
			}
			this.update(state, event);
		}
	}

	protected abstract T getValueInternal();

	/**
	 * This method should ONLY clear values. It should not add values. If you
	 * are tempted to add values, you probably want an actively resetting
	 * tracker -- that is, match BEGIN_TURN and write code in update() that
	 * "resets" on turn changes.
	 */
	protected abstract void reset();

	protected abstract boolean match(GameState state, Event event);

	protected abstract void update(GameState state, Event event);
}
