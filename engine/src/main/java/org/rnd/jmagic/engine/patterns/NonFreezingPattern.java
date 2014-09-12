package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.GameState;
import org.rnd.jmagic.engine.Identified;
import org.rnd.jmagic.engine.Set;
import org.rnd.jmagic.engine.SetPattern;

/**
 * Intended for use where you can safely trivialize the "freeze" method of a set
 * pattern, to reduce 9 lines of anonymous class to half a line of what you
 * really want via lambdas. See the card Military Intelligence for an example.
 */
public class NonFreezingPattern implements SetPattern
{
	private MatcherFunction matcher;

	public NonFreezingPattern(MatcherFunction matcher)
	{
		this.matcher = matcher;
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		return this.matcher.match(state, thisObject, set);
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// no freezing required (that's the point)
	}

}
