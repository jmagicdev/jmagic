package org.rnd.util;

public class NumberRange implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private final Integer lower;
	private final Integer upper;

	/**
	 * Constructs a number range representing all numbers between lower and
	 * upper, inclusive (except when infinite)
	 * 
	 * @param lower The lower end of the range (or null for -inf)
	 * @param upper The upper end of the range (or null for +inf)
	 */
	public NumberRange(Integer lower, Integer upper)
	{
		if((null != lower) && (null != upper) && (upper < lower))
			throw new UnsupportedOperationException("Attempt to assign a number range with an upper bound less than a lower bound");
		this.lower = lower;
		this.upper = upper;
	}

	/**
	 * @param n The number to check
	 * @return True if the number is within the represented range
	 */
	public boolean contains(int n)
	{
		return (this.lower == null || this.lower <= n) && (this.upper == null || this.upper >= n);
	}

	public Integer getLower()
	{
		return this.lower;
	}

	public int getLower(int returnForInfinity)
	{
		return (this.lower == null ? returnForInfinity : this.lower.intValue());
	}

	public Integer getUpper()
	{
		return this.upper;
	}

	public int getUpper(int returnForInfinity)
	{
		return (this.upper == null ? returnForInfinity : this.upper.intValue());
	}

	/**
	 * @return How many numbers are in this range; null if the range is
	 * infinite.
	 */
	public Integer size()
	{
		if(this.lower == null || this.upper == null)
			return null;

		return this.upper - this.lower + 1;
	}

	@Override
	public String toString()
	{
		String infinity = "\u221e";
		return "(" + (this.lower == null ? "-" + infinity : this.lower) + " - " + (this.upper == null ? infinity : this.upper) + ")";
	}
}
