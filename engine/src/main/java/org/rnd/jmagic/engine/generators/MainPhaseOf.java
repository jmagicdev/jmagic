package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players main phase
 */
public class MainPhaseOf extends PhaseOf
{
	public static MainPhaseOf instance(SetGenerator what)
	{
		return new MainPhaseOf(what);
	}

	private MainPhaseOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.PRECOMBAT_MAIN, Phase.PhaseType.POSTCOMBAT_MAIN);
	}
}
