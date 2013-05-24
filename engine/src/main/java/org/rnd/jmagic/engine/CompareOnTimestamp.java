package org.rnd.jmagic.engine;

/**
 * Comparator that compares effects based on their timestamps (earlier effects
 * are sorted first). This comparator is stateful and caches timestamps, so
 * don't use the same one in different states or between refreshes of the same
 * state.
 */
public final class CompareOnTimestamp implements java.util.Comparator<ContinuousEffect>
{
	// We want to cache the timestamps we calculate, in case an effect
	// starts to apply and is then removed (but needs to continue to
	// apply).
	private java.util.Map<ContinuousEffect, Integer> timestamps = new java.util.HashMap<ContinuousEffect, Integer>();

	@Override
	public int compare(ContinuousEffect o1, ContinuousEffect o2)
	{
		int time1 = this.getTimestamp(o1);
		int time2 = this.getTimestamp(o2);
		if(time1 == time2)
			return o1.compareTo(o2);
		return time1 - time2;
	}

	private int getTimestamp(ContinuousEffect e)
	{
		if(this.timestamps.containsKey(e))
			return this.timestamps.get(e);

		int timestamp = e.getTimestamp();
		this.timestamps.put(e, timestamp);
		return timestamp;
	}
}