package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the current step
 */
public class CurrentStep extends SetGenerator
{
	private static final CurrentStep _instance = new CurrentStep();

	public static CurrentStep instance()
	{
		return _instance;
	}

	private CurrentStep()
	{
		// Intentionally left ineffectual
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.currentStep() != null)
			return new Set(state.currentStep());
		return Empty.set;
	}
}
