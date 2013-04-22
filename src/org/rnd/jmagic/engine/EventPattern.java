package org.rnd.jmagic.engine;

/**
 * Used to determine whether an event matches a set of criteria. Instead of
 * matching against a Set returned by each parameter evaluated, each Set
 * returned by a parameter evaluation is matched against a SetPattern
 */
public interface EventPattern
{
	public static class WithContext implements EventPattern
	{
		private EventPattern thePattern;
		// TODO : Seems like we're holding around objects we shouldn't be. Maybe
		// make this an ID?
		private Identified thisObject;

		public WithContext(EventPattern pattern, Identified thisObject)
		{
			this.thePattern = pattern;
			this.thisObject = thisObject;
		}

		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			return this.thePattern.match(event, this.thisObject, state);
		}

		@Override
		public boolean looksBackInTime()
		{
			return this.thePattern.looksBackInTime();
		}

		@Override
		public boolean matchesManaAbilities()
		{
			return this.thePattern.matchesManaAbilities();
		}
	}

	/**
	 * Determines whether a given event matches this pattern. Classes that
	 * override this method should call it (using super.match). NOTE: Any
	 * parameters of the event should be evaluated with the event's source, not
	 * the Identified passed in to this method.
	 * 
	 * @param event The event to check.
	 * @param object The object evaluating this generator (null if a non-object
	 * is causing this match).
	 * @param state The game state in which to check the event against the
	 * pattern.
	 * @return True if the event matches this pattern; false otherwise.
	 */
	public boolean match(Event event, Identified object, GameState state);

	/**
	 * TODO: write this
	 */
	public boolean looksBackInTime();

	public boolean matchesManaAbilities();
}
