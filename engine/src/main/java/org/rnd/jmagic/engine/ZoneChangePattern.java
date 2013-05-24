package org.rnd.jmagic.engine;

/**
 * A pattern for matching against ZoneChanges, for the purposes of determining
 * whether to trigger abilities or apply replacement effects.
 */
public interface ZoneChangePattern
{
	/**
	 * Whether triggered abilities that use this pattern as a trigger event
	 * should look back in time to determine whether to trigger.
	 * 
	 * 603.6d ... Leaves-the-battlefield abilities, ... abilities that trigger
	 * when an object that all players can see is put into a hand or library,
	 * ... will trigger based on their existence, and the appearance of objects,
	 * prior to the event rather than afterward.
	 */
	public boolean looksBackInTime();

	public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state);
}
