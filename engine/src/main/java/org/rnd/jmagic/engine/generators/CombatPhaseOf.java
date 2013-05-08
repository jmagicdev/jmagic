package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players combat phase
 */
public class CombatPhaseOf extends PhaseOf
{
	public static CombatPhaseOf instance(SetGenerator what)
	{
		return new CombatPhaseOf(what);
	}

	private CombatPhaseOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.COMBAT);
	}
}
