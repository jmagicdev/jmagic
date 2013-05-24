package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the previous step
 */
public class PreviousStep extends SetGenerator
{
	private static final PreviousStep _instance = new PreviousStep();

	public static PreviousStep instance()
	{
		return _instance;
	}

	private PreviousStep()
	{
		// Intentionally left ineffectual
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		if(state.previousStep() != null)
			return new Set(state.previousStep());
		return Empty.set;
	}
}
