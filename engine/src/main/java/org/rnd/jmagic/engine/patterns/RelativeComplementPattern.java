package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class RelativeComplementPattern implements SetPattern
{
	private SetPattern left;
	private SetPattern right;

	public RelativeComplementPattern(SetPattern left, SetPattern right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean match(GameState state, Identified object, Set set)
	{
		if(right.match(state, object, set))
			return false;
		return left.match(state, object, set);
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		// Text changes don't affect this.
	}
}
