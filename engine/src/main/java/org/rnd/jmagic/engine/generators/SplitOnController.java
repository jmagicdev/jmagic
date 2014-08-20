package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to a Set of Sets. Each Set contains objects controlled by a single
 * player.
 */
public class SplitOnController extends SetGenerator
{
	public static SplitOnController instance(SetGenerator what)
	{
		return new SplitOnController(what);
	}

	private SetGenerator what;

	private SplitOnController(SetGenerator what)
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
			whoControlsWhat.get(object.controllerID).add(object);
		return Set.fromCollection(whoControlsWhat.values());
	}
}
