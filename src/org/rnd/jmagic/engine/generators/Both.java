package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to an aribtrary nonempty set if both sets are nonempty; empty
 * otherwise.
 */
public class Both extends SetGenerator
{
	public static Both instance(SetGenerator left, SetGenerator right)
	{
		return new Both(left, right);
	}

	private final SetGenerator a;
	private final SetGenerator b;

	private Both(SetGenerator left, SetGenerator right)
	{
		this.a = left;
		this.b = right;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set evalA = this.a.evaluate(state, thisObject);
		if(evalA.isEmpty())
			return Empty.set;

		Set evalB = this.b.evaluate(state, thisObject);
		if(evalB.isEmpty())
			return Empty.set;

		return NonEmpty.set;
	}
}
