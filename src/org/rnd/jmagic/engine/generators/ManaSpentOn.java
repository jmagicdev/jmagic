package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to every ManaSymbol spent on an object
 */
public class ManaSpentOn extends SetGenerator
{
	public static ManaSpentOn instance(SetGenerator what)
	{
		return new ManaSpentOn(what);
	}

	private final SetGenerator set;

	private ManaSpentOn(SetGenerator set)
	{
		this.set = set;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.set.evaluate(state, thisObject).getAll(GameObject.class))
			for(Event e: o.getCostsPaid())
				if(e.type.equals(EventType.PAY_MANA))
					ret.addAll(e.getResult(state).getAll(ManaSymbol.class));
		return ret;
	}
}
