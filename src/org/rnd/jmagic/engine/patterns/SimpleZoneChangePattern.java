package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

/**
 * Simple pattern for matching against most zone changes for use in zone-change
 * triggers and zone-change replacement effects.
 * 
 * Some triggered abilities "look back in time" to determine whether they should
 * trigger. The two kinds of triggers that are of interest to a
 * <code>ZoneChangePattern</code> are leaves-the-battlefield triggers and
 * put-into-hand-or-library triggers. When writing a pattern for one of these
 * kinds of triggers, the <code>oldObject</code> parameter to the constructor
 * should be set to true. When writing any other pattern, it should be set to
 * false.
 */
public class SimpleZoneChangePattern implements ZoneChangePattern
{
	private SetPattern from;
	private SetPattern to;
	private SetPattern what;
	private SetPattern who;
	private boolean oldObject;

	public SimpleZoneChangePattern(SetPattern from, SetPattern to, SetPattern what, boolean oldObject)
	{
		this(from, to, what, null, oldObject);
	}

	public SimpleZoneChangePattern(SetPattern from, SetPattern to, SetPattern what, SetPattern who, boolean oldObject)
	{
		if(from == null)
			this.from = SetPattern.EVERYTHING;
		else
			this.from = from;

		if(to == null)
			this.to = SetPattern.EVERYTHING;
		else
			this.to = to;

		if(who == null)
			this.who = SetPattern.ALWAYS_MATCH;
		else
			this.who = who;

		if(what == null)
			this.what = SetPattern.EVERYTHING;
		else
			this.what = what;

		this.oldObject = oldObject;
	}

	/**
	 * @param from The zone the object must be coming from to match this
	 * pattern; null if it doesn't matter.
	 * @param to The zone the object must be going to; null if it doesn't
	 * matter.
	 * @param what The objects to match against.
	 * @param who The controller of the object.
	 * @param oldObject If true, <code>what</code> matches against the object as
	 * it existed prior to the move; if false, it matches against the object as
	 * it will exist after the move. Set this to true for replacement effects
	 * and look-back triggers; set it to false for all other triggers.
	 */
	public SimpleZoneChangePattern(SetGenerator from, SetGenerator to, SetGenerator what, SetGenerator who, boolean oldObject)
	{
		if(from == null)
			this.from = SetPattern.EVERYTHING;
		else
			this.from = new SimpleSetPattern(from);

		if(to == null)
			this.to = SetPattern.EVERYTHING;
		else
			this.to = new SimpleSetPattern(to);

		if(who == null)
			this.who = SetPattern.ALWAYS_MATCH;
		else
			this.who = new SimpleSetPattern(who);

		if(what == null)
			this.what = SetPattern.EVERYTHING;
		else
			this.what = new SimpleSetPattern(what);

		this.oldObject = oldObject;
	}

	/**
	 * @param from The zone the object must be coming from to match this
	 * pattern; null if it doesn't matter.
	 * @param to The zone the object must be going to; null if it doesn't
	 * matter.
	 * @param what The objects to match against.
	 * @param oldObject If true, <code>what</code> matches against the object as
	 * it existed prior to the move; if false, it matches against the object as
	 * it will exist after the move. Set this to true for replacement effects
	 * and look-back triggers; set it to false for all other triggers.
	 */
	public SimpleZoneChangePattern(SetGenerator from, SetGenerator to, SetGenerator what, boolean oldObject)
	{
		this(from, to, what, null, oldObject);
	}

	@Override
	public boolean looksBackInTime()
	{
		return this.oldObject;
	}

	@Override
	public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
	{
		Set from = new Set(state.get(zoneChange.sourceZoneID));
		if(!this.from.match(state, thisObject, from))
			return false;

		Set to = new Set(state.get(zoneChange.destinationZoneID));
		if(!this.to.match(state, thisObject, to))
			return false;

		Set who = (zoneChange.controllerID == -1 ? new Set() : new Set(state.get(zoneChange.controllerID)));
		if(!this.who.match(state, thisObject, who))
			return false;

		int objectID = this.oldObject ? zoneChange.oldObjectID : zoneChange.newObjectID;
		Set object = new Set(state.get(objectID));
		return this.what.match(state, thisObject, object);
	}
}
