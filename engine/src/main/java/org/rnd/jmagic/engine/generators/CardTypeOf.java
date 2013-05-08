package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the card types of each given GameObject
 */
public class CardTypeOf extends SetGenerator
{
	public static CardTypeOf instance(SetGenerator what)
	{
		return new CardTypeOf(what);
	}

	private final SetGenerator what;

	private CardTypeOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			ret.addAll(object.getTypes());

		return ret;
	}
}
