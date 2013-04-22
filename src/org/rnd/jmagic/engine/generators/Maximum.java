package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the largest of the given numbers
 */
public class Maximum extends SetGenerator
{
	public static Maximum instance(SetGenerator what)
	{
		return new Maximum(what);
	}

	/**
	 * @param numbers A set containing Integers and NumberRanges
	 * @return null if there are no Integers or NumberRanges, or if the upper
	 * bound is positive infinity; otherwise, the greatest Integer or upper
	 * bound of a NumberRange
	 */
	public static Integer get(Set numbers)
	{
		Integer max = null;

		for(Integer n: numbers.getAll(Integer.class))
			if(max == null || (max < n))
				max = n;
		for(org.rnd.util.NumberRange r: numbers.getAll(org.rnd.util.NumberRange.class))
		{
			Integer upper = r.getUpper();
			if(upper == null)
				return null;

			if(max == null || (max < upper))
				max = upper;
		}

		return max;
	}

	private final SetGenerator numbers;

	private Maximum(SetGenerator numbers)
	{
		this.numbers = numbers;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Integer maximum = Maximum.get(this.numbers.evaluate(state, thisObject));
		if(maximum == null)
			return new Set();
		return new Set(maximum);
	}
}
