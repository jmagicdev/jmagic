package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each GameObject which hasn't been under its controllers control
 * since the beginning of his/her last upkeep
 */
public class HasSummoningSickness extends SetGenerator
{
	private static final HasSummoningSickness _instance = new HasSummoningSickness();

	public static HasSummoningSickness instance()
	{
		return _instance;
	}

	private HasSummoningSickness()
	{
		// Intentionally left ineffectual
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		java.util.Set<Integer> summoningSickIDs = new java.util.HashSet<Integer>();
		for(java.util.Collection<Integer> objectIDs: state.summoningSick.values())
			summoningSickIDs.addAll(objectIDs);

		Set ret = new Set();
		objects: for(Integer objectID: summoningSickIDs)
			if(state.containsIdentified(objectID))
			{
				GameObject object = state.get(objectID);
				if(object.isGhost() || !object.getTypes().contains(Type.CREATURE))
					continue;

				for(Keyword k: object.getKeywordAbilities())
					if(k.isHaste())
						continue objects;
				ret.add(object);
			}

		return ret;
	}
}
