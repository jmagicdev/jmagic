package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the power of each of the given GameObjects
 */
public class PowerOf extends SetGenerator
{
	public static PowerOf instance(SetGenerator what)
	{
		return new PowerOf(what);
	}

	private final SetGenerator creatures;

	private PowerOf(SetGenerator creatures)
	{
		this.creatures = creatures;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.creatures.evaluate(state, thisObject).getAll(GameObject.class))
			// 207.3. A noncreature permanent has no power or toughness, even if
			// it's a card with a power and toughness printed on it.
			// I'm assuming here that a nonpermanent object isn't going to have
			// p/t if it isn't a creature card.
			if(o.getTypes().contains(Type.CREATURE))
				ret.add(o.getPower());
		return ret;
	}
}
