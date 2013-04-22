package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class PlayerCasting extends SetGenerator
{
	public static PlayerCasting instance(SetGenerator what)
	{
		return new PlayerCasting(what);
	}

	private final SetGenerator what;

	private PlayerCasting(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(GameObject o: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			if(-1 != o.playerCasting)
				ret.add(state.<Player>get(o.playerCasting));
		return ret;
	}
}
