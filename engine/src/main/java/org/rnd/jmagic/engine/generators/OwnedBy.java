package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the objects owned by the given players in any zone
 */
public class OwnedBy extends SetGenerator
{
	public static OwnedBy instance(SetGenerator ownerGenerator)
	{
		return new OwnedBy(ownerGenerator);
	}

	private final SetGenerator owner;

	private OwnedBy(SetGenerator ownerGenerator)
	{
		this.owner = ownerGenerator;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		java.util.Set<Integer> owners = new java.util.HashSet<Integer>();
		for(Player player: this.owner.evaluate(state, thisObject).getAll(Player.class))
			owners.add(player.ID);

		for(GameObject object: state.getAllObjects())
			if(owners.contains(object.ownerID))
				ret.add(object);
		return ret;
	}
}
