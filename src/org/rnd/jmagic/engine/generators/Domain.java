package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Counts the basic land types among lands that a given player controls. Note
 * that the returned Set will contain a single integer (or else an empty set if
 * no player was found in the provided set)
 */
public class Domain extends SetGenerator
{
	public static Domain instance(SetGenerator players)
	{
		return new Domain(players);
	}

	private SetGenerator players;

	private Domain(SetGenerator players)
	{
		this.players = players;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Player player = this.players.evaluate(state, thisObject).getOne(Player.class);
		if(player == null)
			return Empty.set;

		// We're not interested in ALL the land types of the objects, only the
		// BASIC land types. Rather than checking each of the objects' land
		// types to see if they are basic, we keep track of the basic land types
		// the player DOESN'T have and then subtract the result from the total
		// number of basic land types.
		java.util.Set<SubType> types = java.util.EnumSet.copyOf(SubType.getBasicLandTypes());

		for(GameObject object: state.battlefield().objects)
			if(object.getTypes().contains(Type.LAND) && player.ID == object.controllerID)
				types.removeAll(object.getSubTypes());

		int basicLandTypes = SubType.getBasicLandTypes().size();
		return new Set(basicLandTypes - types.size());
	}
}
