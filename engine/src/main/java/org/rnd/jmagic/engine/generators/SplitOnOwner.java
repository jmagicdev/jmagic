package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to a Set of Sets. Each Set contains objects owned by a single
 * player.
 */
public class SplitOnOwner extends SetGenerator
{
	public static SplitOnOwner instance(SetGenerator what)
	{
		return new SplitOnOwner(what);
	}

	private SetGenerator what;

	private SplitOnOwner(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Map<Integer, Set> whoControlsWhat = new java.util.HashMap<Integer, Set>();
		for(Player p: state.players)
			whoControlsWhat.put(p.ID, new Set());
		for(GameObject object: this.what.evaluate(state, thisObject).getAll(GameObject.class))
			whoControlsWhat.get(object.ownerID).add(object);
		return new Set(whoControlsWhat.values());
	}
}
