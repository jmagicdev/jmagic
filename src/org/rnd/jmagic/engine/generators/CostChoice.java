package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class CostChoice extends SetGenerator
{
	public static CostChoice instance(SetGenerator who, EventFactory factory)
	{
		factory.preserveCreatedEvents();
		return new CostChoice(who, Identity.instance(factory));
	}

	private final SetGenerator who;
	private final SetGenerator factories;

	private CostChoice(SetGenerator who, SetGenerator factories)
	{
		this.who = who;
		this.factories = factories;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set players = this.who.evaluate(state, thisObject);

		Set ret = new Set();
		if(thisObject.isGameObject())
		{
			GameObject o = (GameObject)thisObject;
			for(EventFactory f: this.factories.evaluate(state, thisObject).getAll(EventFactory.class))
			{
				Event costGenerated = o.getCostGenerated(state, f);
				if(null != costGenerated)
				{
					for(Player p: players.getAll(Player.class))
						ret.addAll(costGenerated.getChoices(p));
				}
			}
		}
		return ret;
	}
}
