package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players upkeep step
 */
public class UpkeepStepOf extends StepOf
{
	public static UpkeepStepOf instance(SetGenerator what)
	{
		return new UpkeepStepOf(what);
	}

	private UpkeepStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.BEGINNING, Step.StepType.UPKEEP);
	}
}
