package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players untap step
 */
public class UntapStepOf extends StepOf
{
	public static UntapStepOf instance(SetGenerator what)
	{
		return new UntapStepOf(what);
	}

	private UntapStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.BEGINNING, Step.StepType.UNTAP);
	}
}
