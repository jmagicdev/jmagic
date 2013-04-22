package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Impersonate will evaluate a set generator in a different context and return
 * the result. This is particularly useful for replacement effects who need to
 * evaluate the CAUSE of the replaced event in the same way that the replaced
 * event would. In terms of set generators, this will evaluate a generator in
 * terms of its own thisObject, and then evaluate another generator using the
 * object returned by the first generator as the thisObject.
 */
public class Impersonate extends SetGenerator
{
	public static Impersonate instance(SetGenerator newContext, SetGenerator generator)
	{
		return new Impersonate(newContext, Identity.instance(generator));
	}

	private final SetGenerator newContext;
	private final SetGenerator generator;

	private Impersonate(SetGenerator newContext, SetGenerator generator)
	{
		this.newContext = newContext;
		this.generator = generator;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Identified newThisObject = this.newContext.evaluate(state, thisObject).getOne(Identified.class);
		return this.generator.evaluate(state, thisObject).getOne(SetGenerator.class).evaluate(state, newThisObject);
	}

}
