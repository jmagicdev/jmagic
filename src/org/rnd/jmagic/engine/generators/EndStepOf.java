package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players end of turn step
 */
public class EndStepOf extends StepOf
{
	public static EndStepOf instance(SetGenerator what)
	{
		return new EndStepOf(what);
	}

	private EndStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.ENDING, Step.StepType.END);
	}
}
