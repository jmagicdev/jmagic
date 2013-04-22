package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each element in either of the given sets
 */
public class Union extends SetGenerator
{
	public static Union instance(SetGenerator left, SetGenerator right, SetGenerator... generators)
	{
		Union ret = new Union(left, right);
		for(int i = 0; i < generators.length; ++i)
			ret = new Union(ret, generators[i]);

		return ret;
	}

	static public Set get(Set a, Set b)
	{
		Set ret = new Set(a);
		ret.addAll(b);
		return ret;
	}

	private final SetGenerator a;
	private final SetGenerator b;

	private Union(SetGenerator left, SetGenerator right)
	{
		this.a = left;
		this.b = right;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return Union.get(this.a.evaluate(state, thisObject), this.b.evaluate(state, thisObject));
	}
}
