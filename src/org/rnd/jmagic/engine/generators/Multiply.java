package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the product of each number in one set with each number in a
 * second set
 */
public class Multiply extends SetGenerator
{
	public static Multiply instance(SetGenerator left, SetGenerator right)
	{
		return new Multiply(left, right);
	}

	private final SetGenerator left;
	private final SetGenerator right;

	private Multiply(SetGenerator left, SetGenerator right)
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

		// For each pair of numbers in left and right, add their product
		for(Integer l: left)
			for(Integer r: right)
				ret.add(l * r);

		return ret;
	}
}
