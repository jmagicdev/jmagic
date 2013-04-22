package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players combat damage step
 */
public class CombatDamageStepOf extends StepOf
{
	public static CombatDamageStepOf instance(SetGenerator what)
	{
		return new CombatDamageStepOf(what);
	}

	private CombatDamageStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.COMBAT, Step.StepType.COMBAT_DAMAGE);
	}
}
