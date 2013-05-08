package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class DivideBy extends SetGenerator
{
	// TODO : Figure out what to do when denominator is zero. Then change the
	// following cards to allow selecting zero targets:
	// Fireball
	// Hail of Arrows

	public static DivideBy instance(SetGenerator numerator, SetGenerator denominator, boolean roundUp)
	{
		return new DivideBy(numerator, denominator, roundUp);
	}

	public static int get(int numerator, int denominator, boolean roundUp)
	{
		if(denominator == 0)
			// 107.2. If anything needs to use a number that can't be
			// determined, either as a result or in a calculation, it uses 0
			// instead.
			return 0;

		double result = numerator / (double)denominator;
		if(roundUp)
			return (int)(Math.ceil(result));
		return (int)(Math.floor(result));
	}

	private final SetGenerator numerator;
	private final SetGenerator denominator;
	private final boolean roundUp;

	private DivideBy(SetGenerator numerator, SetGenerator denominator, boolean roundUp)
	{
		this.numerator = numerator;
		this.denominator = denominator;
		this.roundUp = roundUp;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Set<Integer> denominators = this.denominator.evaluate(state, thisObject).getAll(Integer.class);
		for(Integer n: this.numerator.evaluate(state, thisObject).getAll(Integer.class))
			for(Integer d: denominators)
				ret.add(DivideBy.get(n, d, this.roundUp));
		return ret;
	}
}
