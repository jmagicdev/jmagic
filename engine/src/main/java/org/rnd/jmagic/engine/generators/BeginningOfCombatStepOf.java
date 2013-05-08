package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players cleanup step
 */
public class BeginningOfCombatStepOf extends StepOf
{
	public static StepOf instance(SetGenerator players)
	{
		return new BeginningOfCombatStepOf(players);
	}

	private BeginningOfCombatStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.COMBAT, Step.StepType.BEGINNING_OF_COMBAT);
	}
}
