package org.rnd.jmagic.engine;

/**
 * This is a class for matching elements of a Set and returning similarly
 * matched elements grouped together.
 */
public interface GroupingPattern
{
	public java.util.Set<Set> match(Set set, Identified thisObject, GameState state);
}
