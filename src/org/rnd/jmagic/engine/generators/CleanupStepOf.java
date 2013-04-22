package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players cleanup step
 */
public class CleanupStepOf extends StepOf
{
	public static StepOf instance(SetGenerator players)
	{
		return new CleanupStepOf(players);
	}

	private CleanupStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.ENDING, Step.StepType.CLEANUP);
	}
}
