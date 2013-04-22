package org.rnd.jmagic.engine;

/** Represents something that can be owned. */
public interface Ownable
{
	public Player getOwner(GameState state);
}
