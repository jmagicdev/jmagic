package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the object(s) with the given id(s)
 */
public class IdentifiedWithID extends SetGenerator
{
	public static IdentifiedWithID instance(int what)
	{
		return new IdentifiedWithID(what);
	}

	public static IdentifiedWithID instance(java.util.Collection<Integer> what)
	{
		return new IdentifiedWithID(what);
	}

	private final java.util.Collection<Integer> IDs;

	private IdentifiedWithID(int ID)
	{
		this.IDs = new java.util.LinkedList<Integer>();
		this.IDs.add(ID);
	}

	private IdentifiedWithID(java.util.Collection<Integer> IDs)
	{
		this.IDs = new java.util.LinkedList<Integer>(IDs);
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Integer n: this.IDs)
			if(state.containsIdentified(n))
				ret.add(state.get(n));
		return ret;
	}
}
