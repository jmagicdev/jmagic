package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class Blocked extends SetGenerator
{
	private static SetGenerator _instance = null;

	public static SetGenerator instance()
	{
		if(_instance == null)
			_instance = new Blocked();
		return _instance;
	}

	private Blocked()
	{
		// singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(!state.currentPhase().blockersDeclared)
			return Empty.set;

		Set creatures = CreaturePermanents.instance().evaluate(state, thisObject);
		Set ret = new Set();

		for(GameObject object: creatures.getAll(GameObject.class))
			if(object.getAttackingID() != -1 && object.getBlockedByIDs() != null)
				ret.add(object);

		return ret;
	}
}
