package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class DiscardPattern implements ZoneChangePattern
{
	private SetPattern what;

	public DiscardPattern(SetPattern what)
	{
		this.what = what;
	}

	public DiscardPattern(SetGenerator what)
	{
		this.what = new SimpleSetPattern(what);
	}

	@Override
	public boolean looksBackInTime()
	{
		return false;
	}

	@Override
	public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
	{
		if(!zoneChange.isDiscard)
			return false;

		// TODO : verify that this is supposed to be the old object. I feel like
		// it should be the new object, but I don't think that would work for
		// Madness (a replacement effect)
		int objectID = zoneChange.oldObjectID;
		Set object = new Set(state.get(objectID));
		return this.what.match(state, thisObject, object);
	}

}
