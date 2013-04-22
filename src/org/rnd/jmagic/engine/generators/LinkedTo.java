package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class LinkedTo extends SetGenerator
{
	public static LinkedTo instance(SetGenerator what)
	{
		return new LinkedTo(what);
	}

	private SetGenerator what;

	private LinkedTo(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Linkable i: this.what.evaluate(state, thisObject).getAll(Linkable.class))
		{
			Linkable.Manager manager = i.getLinkManager();
			for(Class<? extends Linkable> linkClass: manager.getLinkClasses())
			{
				Linkable link = manager.getLink(state, linkClass);
				if(link != null)
					ret.add(link);
			}
		}
		return ret;
	}
}
