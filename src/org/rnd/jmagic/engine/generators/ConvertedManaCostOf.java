package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the converted mana cost of each given GameObject. Since Set
 * supports duplicity amongst Integers, the size of this set will be the number
 * of GameObjects passed to it.
 */
public class ConvertedManaCostOf extends SetGenerator
{
	public static ConvertedManaCostOf instance(SetGenerator what)
	{
		return new ConvertedManaCostOf(what);
	}

	private final SetGenerator what;

	private ConvertedManaCostOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			ret.add(object.getConvertedManaCost());
		return ret;
	}

}
