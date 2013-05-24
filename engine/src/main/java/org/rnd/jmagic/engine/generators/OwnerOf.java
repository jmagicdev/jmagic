package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the owner of each given Ownable
 */
public class OwnerOf extends SetGenerator
{
	public static OwnerOf instance(SetGenerator what)
	{
		return new OwnerOf(what);
	}

	private final SetGenerator what;

	private OwnerOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Ownable o: this.what.evaluate(state, thisObject).getAll(Ownable.class))
			ret.add(state.<Player>get(o.getOwner(state).ID));
		return ret;
	}
}
