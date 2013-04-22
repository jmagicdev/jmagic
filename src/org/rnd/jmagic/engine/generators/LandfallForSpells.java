package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * If a land entered the battlefield under your control this turn...
 * 
 * Requires the {@link LandsPutOntoTheBattlefieldThisTurnCounter} flag.
 */
public class LandfallForSpells extends SetGenerator
{
	private static SetGenerator _instance = new LandfallForSpells();

	public static SetGenerator instance()
	{
		return _instance;
	}

	private LandfallForSpells()
	{
		// singleton generator
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		int controller = state.<GameObject>get(thisObject.ID).controllerID;
		java.util.Map<Integer, Integer> flagValue = state.getTracker(LandsPutOntoTheBattlefieldThisTurnCounter.class).getValue(state);

		if(!flagValue.containsKey(controller))
			return Empty.set;

		if(flagValue.get(controller) == 0)
			return Empty.set;

		return NonEmpty.set;
	}
}
