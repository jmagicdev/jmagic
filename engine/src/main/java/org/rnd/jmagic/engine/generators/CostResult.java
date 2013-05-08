package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Like {@link EffectResult}, except that it works on costs. NOTE: If you are
 * referencing a cost result in a targeting restriction, you are Doing It Wrong!
 * You almost certainly want {@link CostChoice}, as costs have not been paid
 * when targets are declared (but cards are often worded as though they have
 * been). See {@link org.rnd.jmagic.cards.SimicManipulator}.
 */
public class CostResult extends SetGenerator
{
	public static CostResult instance(EventFactory factory)
	{
		factory.preserveCreatedEvents();
		return new CostResult(Identity.instance(factory));
	}

	private final SetGenerator factories;

	private CostResult(SetGenerator factories)
	{
		this.factories = factories;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		if(thisObject.isGameObject())
		{
			GameObject o = (GameObject)thisObject;
			for(EventFactory f: this.factories.evaluate(state, thisObject).getAll(EventFactory.class))
			{
				Event costGenerated = o.getCostGenerated(state, f);
				if(null != costGenerated)
					ret.addAll(costGenerated.getResult(state));
			}
		}
		return ret;
	}
}
