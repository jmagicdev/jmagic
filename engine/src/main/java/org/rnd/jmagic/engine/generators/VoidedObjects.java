package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to all objects that are destined for the battlefield but haven't
 * gotten there yet, for the purposes of applying their static abilities to
 * themselves to determine whether enters-the-battlefield replacement effects
 * should apply to them.
 */
public class VoidedObjects extends SetGenerator
{
	private static final VoidedObjects _instance = new VoidedObjects();

	public static VoidedObjects instance()
	{
		return _instance;
	}

	private VoidedObjects()
	{
		// Intentionally non-functional
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		return Set.fromCollection(state.voidedObjects);
	}
}
