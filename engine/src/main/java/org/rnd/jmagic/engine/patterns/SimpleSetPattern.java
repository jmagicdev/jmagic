package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Compares two setgenerators by intersecting their evaluations. If you are
 * looking for a pattern that matches objects that have a specific Enumeration
 * property (Type or {@link Color}, for example), use a pattern specifically
 * designed for that. This pattern's freeze method is trivial.
 */
public class SimpleSetPattern implements SetPattern
{
	private SetGenerator pattern;

	/**
	 * @param pattern The pattern to match against
	 */
	public SimpleSetPattern(SetGenerator pattern)
	{
		this.pattern = pattern;
	}

	/**
	 * Compares a set against the pattern by intersection
	 * 
	 * @param state The context in which to evaluate the pattern
	 * @param object The object to evaluate set generators with
	 * @param set The set to match against
	 * @return Whether or not the evaluated pattern and the set share any
	 * elements
	 */
	@Override
	public boolean match(GameState state, Identified object, Set set)
	{
		return !Intersect.get(set, this.pattern.evaluate(state, object)).isEmpty();
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// Text changes don't affect this.
	}
}
