package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players declare blockers step
 */
public class DeclareAttackersStepOf extends StepOf
{
	public static DeclareAttackersStepOf instance(SetGenerator what)
	{
		return new DeclareAttackersStepOf(what);
	}

	private DeclareAttackersStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.COMBAT, Step.StepType.DECLARE_ATTACKERS);
	}
}
