package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

/**
 * Intended for use where you can safely trivialize the "freeze" method of a set
 * pattern, to reduce 9 lines of anonymous class to half a line of what you
 * really want via lambdas. See the card Military Intelligence for an example.
 */
@FunctionalInterface
public interface SetMatcherFunction
{
	public boolean match(GameState state, Identified thisObject, Set set);
}
