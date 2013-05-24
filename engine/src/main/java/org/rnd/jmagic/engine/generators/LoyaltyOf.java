package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given planeswalkers loyalty number if not a permanent, or
 * the number of loyalty counters on it if it is a permanent.
 */
public class LoyaltyOf extends SetGenerator
{
	public static LoyaltyOf instance(SetGenerator what)
	{
		return new LoyaltyOf(what);
	}

	private final SetGenerator objects;

	public static int get(GameObject object)
	{
		if(object.getTypes().contains(Type.PLANESWALKER))
		{
			if(object.isPermanent())
				return CountersOn.get(object, Counter.CounterType.LOYALTY).size();
			return object.getPrintedLoyalty();
		}
		return 0;
	}

	private LoyaltyOf(SetGenerator objects)
	{
		this.objects = objects;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();

		for(GameObject object: this.objects.evaluate(state, thisObject).getAll(GameObject.class))
			ret.add(LoyaltyOf.get(object));

		return ret;
	}
}
