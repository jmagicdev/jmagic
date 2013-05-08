package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ListGet extends SetGenerator
{
	public static ListGet instance(SetGenerator list, int index)
	{
		return new ListGet(list, index);
	}

	private SetGenerator list;
	private int index;

	private ListGet(SetGenerator list, int index)
	{
		this.list = list;
		this.index = index;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(java.util.List<?> list: this.list.evaluate(state, thisObject).getAll(java.util.List.class))
			if(list.size() > this.index)
				ret.add(list.get(this.index));
		return ret;
	}
}
