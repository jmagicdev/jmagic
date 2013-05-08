package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players draw step
 */
public class DrawStepOf extends StepOf
{
	public static DrawStepOf instance(SetGenerator what)
	{
		return new DrawStepOf(what);
	}

	private DrawStepOf(SetGenerator players)
	{
		super(players, Phase.PhaseType.BEGINNING, Step.StepType.DRAW);
	}
}
