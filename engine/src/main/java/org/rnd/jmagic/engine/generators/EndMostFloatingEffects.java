package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If "until end of turn" and "this turn" effects have ended for this turn,
 * evaluates to nonempty; otherwise evaluates to empty.
 */
public class EndMostFloatingEffects extends SetGenerator
{
	private static final EndMostFloatingEffects _instance = new EndMostFloatingEffects();

	public static SetGenerator instance()
	{
		return _instance;
	}

	private EndMostFloatingEffects()
	{
		// singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.currentTurn().endEffects)
			return NonEmpty.set;
		return Empty.set;
	}

}
