package org.rnd.jmagic.engine;

/**
 * Represents things that can be controlled. Some things that fall into this
 * category are turn structure items and game objects.
 */
public interface Controllable extends Ownable
{
	public Player getController(GameState state);
}
