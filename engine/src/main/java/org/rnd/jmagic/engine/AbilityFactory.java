package org.rnd.jmagic.engine;

public interface AbilityFactory
{
	public Identified create(GameState state, Identified thisObject);

	/** temporary while we still have the old-style granted abilities around. */
	public Class<?> clazz();
}
