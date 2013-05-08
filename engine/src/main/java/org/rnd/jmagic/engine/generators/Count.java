package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the number of elements in the given set
 */
public class Count extends SetGenerator
{
	public static Count instance(SetGenerator what)
	{
		return new Count(what);
	}

	private final SetGenerator set;

	private Count(SetGenerator set)
	{
		this.set = set;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return new Set(this.set.evaluate(state, thisObject).size());
	}

	@Override
	public boolean isGreaterThanZero(Game game, GameObject thisObject) throws NoSuchMethodException
	{
		return !this.set.evaluate(game.actualState, thisObject).isEmpty();
	}
}
