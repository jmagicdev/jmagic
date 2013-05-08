package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

/**
 * A SetPattern that matches against multiple categories of criteria (called
 * subpatterns).
 * 
 * Note that a multiple set pattern with no subpatterns will match no sets in
 * 'any' mode, since there are no patterns to match against, but will match all
 * sets in 'all' mode, since there are no patterns to fail to match against.
 */
public class MultipleSetPattern implements SetPattern
{
	private boolean all;

	/**
	 * Creates a new MultipleSetPattern.
	 * 
	 * @param all If true, sets only match this pattern if they match all
	 * subpatterns. Otherwise, sets match this pattern if they match any
	 * subpatterns.
	 */
	public MultipleSetPattern(boolean all)
	{
		this.all = all;
	}

	private java.util.Collection<SetPattern> patterns = new java.util.LinkedList<SetPattern>();

	/** Adds subpatterns to this multiple set pattern. */
	public void addPattern(SetPattern pattern)
	{
		this.patterns.add(pattern);
	}

	/**
	 * @return True if the given set matches ANY or ALL subpatterns, depending
	 * on how this pattern was constructed.
	 */
	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		if(this.all)
		{
			for(SetPattern pattern: this.patterns)
				if(!pattern.match(state, thisObject, set))
					return false;

			return true;
		}

		// any
		for(SetPattern pattern: this.patterns)
			if(pattern.match(state, thisObject, set))
				return true;

		return false;
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		for(SetPattern pattern: this.patterns)
			pattern.freeze(state, thisObject);
	}

}
