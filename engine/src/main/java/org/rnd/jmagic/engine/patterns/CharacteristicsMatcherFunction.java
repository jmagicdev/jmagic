package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

@FunctionalInterface
public interface CharacteristicsMatcherFunction
{
	public boolean match(Characteristics c);
}
