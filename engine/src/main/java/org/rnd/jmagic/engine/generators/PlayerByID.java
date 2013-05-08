package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the player(s) with the given id(s)
 */
public class PlayerByID extends SetGenerator
{
	public static PlayerByID instance(int what)
	{
		return new PlayerByID(what);
	}

	public static PlayerByID instance(java.util.Collection<Integer> what)
	{
		return new PlayerByID(what);
	}

	private final java.util.Collection<Integer> IDs;

	private PlayerByID(int ID)
	{
		this.IDs = new java.util.LinkedList<Integer>();
		this.IDs.add(ID);
	}

	private PlayerByID(java.util.Collection<Integer> IDs)
	{
		this.IDs = new java.util.LinkedList<Integer>(IDs);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Integer n: this.IDs)
			ret.add(state.get(n));
		return ret;
	}
}
