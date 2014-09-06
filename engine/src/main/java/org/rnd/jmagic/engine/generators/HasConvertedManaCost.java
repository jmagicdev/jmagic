package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject whose mana cost is either given, or contained in
 * the given NumberRange
 */
public class HasConvertedManaCost extends SetGenerator
{
	public static HasConvertedManaCost instance(SetGenerator what)
	{
		return new HasConvertedManaCost(what);
	}

	private final SetGenerator numbers;

	private HasConvertedManaCost(SetGenerator number)
	{
		this.numbers = number;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		java.util.Set<Integer> numbers = this.numbers.evaluate(state, thisObject).getAll(Integer.class);
		org.rnd.util.NumberRange range = this.numbers.evaluate(state, thisObject).getOne(org.rnd.util.NumberRange.class);

		objectLoop: for(GameObject object: state.getAllObjects())
		{
			int[] convertedCost = object.getConvertedManaCost();

			for(Integer n: numbers)
				for(Integer c: convertedCost)
					if(c == n)
					{
						ret.add(object);
						continue objectLoop;
					}
			if(range != null)
				for(Integer c: convertedCost)
					if(range.contains(c))
					{
						ret.add(object);
						continue objectLoop;
					}
		}

		return ret;
	}
}
