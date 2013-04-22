package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players declare blockers step
 */
public class EndOfCombatStepOf extends StepOf
{
	public static EndOfCombatStepOf instance(SetGenerator what)
	{
		return new EndOfCombatStepOf(what);
	}

	private EndOfCombatStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.COMBAT, Step.StepType.END_OF_COMBAT);
	}
}
