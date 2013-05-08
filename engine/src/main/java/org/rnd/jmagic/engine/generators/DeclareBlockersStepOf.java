package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players declare blockers step
 */
public class DeclareBlockersStepOf extends StepOf
{
	public static DeclareBlockersStepOf instance(SetGenerator what)
	{
		return new DeclareBlockersStepOf(what);
	}

	private DeclareBlockersStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.COMBAT, Step.StepType.DECLARE_BLOCKERS);
	}
}
