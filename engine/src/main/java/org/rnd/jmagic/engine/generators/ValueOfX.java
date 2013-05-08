package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the x value of each given GameObject, if it uses X
 */
public class ValueOfX extends SetGenerator
{
	public static ValueOfX instance(SetGenerator what)
	{
		return new ValueOfX(what);
	}

	private final SetGenerator what;

	private ValueOfX(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		java.util.Set<GameObject> objects = this.what.evaluate(state, thisObject).getAll(GameObject.class);

		for(GameObject object: objects)
			if(object.getValueOfX() >= 0)
				ret.add(object.getValueOfX());
			else
				// This is for when you cast an X spell without paying its mana
				// cost -- anything that asks for the value of X should get 0.
				// (See Epic Experiment for why this might be important)
				ret.add(0);
		return ret;
	}

	@Override
	public boolean isGreaterThanZero(Game game, GameObject thisObject) throws NoSuchMethodException
	{
		// Whenever X is involved in a cost, assume the cost can be paid.
		return true;
	}
}
