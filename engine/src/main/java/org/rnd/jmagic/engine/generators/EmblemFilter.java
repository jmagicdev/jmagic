package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class EmblemFilter extends SetGenerator
{
	public static EmblemFilter instance(SetGenerator what)
	{
		return new EmblemFilter(what);
	}

	private SetGenerator what;

	private EmblemFilter(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return Set.fromCollection(this.what.evaluate(state, thisObject).getAll(Emblem.class));
	}
}
