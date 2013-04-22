package org.rnd.jmagic.engine;

/** DAMN YOU EXTRA LANDS, DAMN YOU TO HELL */
abstract public class PlayLandActionFactory
{
	public final Game game;

	public PlayLandActionFactory(Game game)
	{
		this.game = game;
	}

	abstract public PlayLandAction createAction(Player who, GameObject object);
}
