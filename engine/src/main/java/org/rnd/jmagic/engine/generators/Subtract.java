package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the difference of each number in one set with each number in
 * another set
 */
public class Subtract extends SetGenerator
{
	public static Subtract instance(SetGenerator left, SetGenerator right)
	{
		return new Subtract(left, right);
	}

	private final SetGenerator left;
	private final SetGenerator right;

	private Subtract(SetGenerator left, SetGenerator right)
	{
		this.left = left;
		this.right = right;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> left = this.left.evaluate(state, thisObject).getAll(Integer.class);
		java.util.Set<Integer> right = this.right.evaluate(state, thisObject).getAll(Integer.class);
		Set ret = new Set();

		// For each pair of numbers in left and right, add their difference
		for(Integer l: left)
			for(Integer r: right)
				ret.add(l - r);

		return ret;
	}
}
