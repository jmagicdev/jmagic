package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to "this" in the context of evaluation. Note, this is one of the
 * few setgenerators which will return ghosts.
 */
public class This extends SetGenerator
{
	private static final This _instance = new This();

	public static This instance()
	{
		return _instance;
	}

	private This()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.containsIdentified(thisObject.ID))
			return new Set(state.<Identified>get(thisObject.ID));

		// Anything that gets this far is probably coded wrong, so return null
		// so the program will break in a relevant place.
		return null;
	}
}
