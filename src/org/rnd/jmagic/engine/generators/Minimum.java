package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the largest of the given numbers
 */
public class Minimum extends SetGenerator
{
	public static Minimum instance(SetGenerator what)
	{
		return new Minimum(what);
	}

	/**
	 * @param numbers A set containing Integers and NumberRanges
	 * @return null if there are no Integers or NumberRanges, or if the lower
	 * bound is negative infinity; otherwise, the lowest Integer or lower bound
	 * of a NumberRange
	 */
	public static Integer get(Set numbers)
	{
		Integer min = null;

		for(Integer n: numbers.getAll(Integer.class))
			if(min == null || (n < min))
				min = n;
		for(org.rnd.util.NumberRange r: numbers.getAll(org.rnd.util.NumberRange.class))
		{
			Integer lower = r.getLower();
			if(lower == null)
				return null;

			if(min == null || (lower < min))
				min = lower;
		}

		return min;
	}

	static public void main(String args[])
	{
		Set a = new Set();
		a.add(8);
		a.add(new org.rnd.util.NumberRange(1, 9));
		System.out.println("a = " + a);
		System.out.println("Minimum.get(a) = " + Minimum.get(a));
	}

	private final SetGenerator numbers;

	private Minimum(SetGenerator numbers)
	{
		this.numbers = numbers;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return new Set(Minimum.get(this.numbers.evaluate(state, thisObject)));
	}
}
