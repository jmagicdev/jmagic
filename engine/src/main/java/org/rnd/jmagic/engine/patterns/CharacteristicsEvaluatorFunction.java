package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

@FunctionalInterface
public interface CharacteristicsEvaluatorFunction
{
	public boolean match(Characteristics c, Set set);
}
