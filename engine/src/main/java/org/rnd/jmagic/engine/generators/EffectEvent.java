package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class EffectEvent extends SetGenerator
{
	public static EffectEvent instance(EventFactory factory)
	{
		factory.preserveCreatedEvents();
		return new EffectEvent(Identity.instance(factory));
	}

	private final SetGenerator factories;

	private EffectEvent(SetGenerator factories)
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
				Event effectGenerated = o.getEffectGenerated(state, f);
				if(null != effectGenerated)
					ret.add(effectGenerated);
			}
		}
		return ret;
	}

}
