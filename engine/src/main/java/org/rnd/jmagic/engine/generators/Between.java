package org.rnd.jmagic.engine.generators;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to a numberrange
 */
public class Between extends SetGenerator
{
	/**
	 * Creates a SetGenerator that evaluates to a range of numbers.
	 * 
	 * @param lowerBound The inclusive lower bound of the range. If this
	 * parameter is null, the lower bound is assumed to be negative infinity.
	 * @param upperBound The inclusive upper bound of the range. If this
	 * parameter is null, the upper bound is assumed to be positive infinity.
	 * @return A generator evaluating to the range specified by the bound
	 * parameters.
	 */
	public static Between instance(Integer lowerBound, Integer upperBound)
	{
		SetGenerator lower = (lowerBound == null) ? Empty.instance() : numberGenerator(lowerBound);
		SetGenerator upper = (upperBound == null) ? Empty.instance() : numberGenerator(upperBound);

		return new Between(lower, upper);
	}

	/**
	 * Creates a SetGenerator that evaluates to a range of numbers.
	 * 
	 * @param lowerBound The inclusive lower bound of the range. If the
	 * generator evaluates to a set that doesn't contain a number or is null,
	 * the lower bound is assumed to be negative infinity.
	 * @param upperBound The inclusive upper bound of the range. If the
	 * generator evaluates to a set that doesn't contain a number or is null,
	 * the upper bound is assumed to be positive infinity.
	 * @return A generator evaluating to the range specified by the bound
	 * parameters.
	 */
	public static Between instance(SetGenerator lowerBound, SetGenerator upperBound)
	{
		return new Between(lowerBound, upperBound);
	}

	private final SetGenerator lowerBound;
	private final SetGenerator upperBound;

	private Between(SetGenerator lowerBound, SetGenerator upperBound)
	{
		this.lowerBound = (lowerBound == null) ? Empty.instance() : lowerBound;
		this.upperBound = (upperBound == null) ? Empty.instance() : upperBound;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Integer lowerNumber = this.lowerBound.evaluate(state, thisObject).getOne(Integer.class);
		Integer upperNumber = this.upperBound.evaluate(state, thisObject).getOne(Integer.class);
		return new Set(new org.rnd.util.NumberRange(lowerNumber, upperNumber));
	}
}
